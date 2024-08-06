package com.exam.myapp.ui.pokemon_detail

import android.annotation.SuppressLint
import android.service.autofill.FillEventHistory.Event
import androidx.lifecycle.MutableLiveData
import com.exam.myapp.data.local.room.PokemonDetailEntity
import com.exam.myapp.data.repository.PokemonDetailRepository
import com.exam.myapp.ui.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class PokemonDetailViewModel @Inject constructor(private val repository: PokemonDetailRepository) :BaseViewModel() {

    var disposable: Disposable? = null
    val pokemonDetail : MutableLiveData<PokemonDetailEntity> = MutableLiveData()
    var pokemonName = ""

    val isBack = MutableLiveData<Boolean>()

    override fun start() {
        getPokemonDetailByName(pokemonName)
    }

    @SuppressLint("CheckResult")
    fun getPokemonDetailByName(name: String) {
        disposable = repository.getPokemonDetail(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loadingVisibility.value = true }
            .doAfterTerminate { loadingVisibility.value = false }
            .subscribe({ resp ->
                pokemonDetail.value = resp

            }, { error ->
                errorMessage.value = error.message
                repository.getPokemonDetailFromLocal(name)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        pokemonDetail.value = it
                    }, {})
            })
    }

    fun onBack() {
        isBack.value = true
    }

    public override fun onCleared() {
        super.onCleared()
        disposable?.let { if (!it.isDisposed) it.dispose() }
    }
}