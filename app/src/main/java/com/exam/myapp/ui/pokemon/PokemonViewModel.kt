package com.exam.myapp.ui.pokemon

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.exam.myapp.data.local.room.PokemonEnity
import com.exam.myapp.data.repository.PokemonRepository
import com.exam.myapp.ui.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


class PokemonViewModel @Inject constructor(private val repository: PokemonRepository) :BaseViewModel() {

    var disposable: Disposable? = null

    val allPokemonList : MutableLiveData<MutableList<PokemonEnity>> = MutableLiveData()
    val pokemonListDisplayed : MutableLiveData<MutableList<PokemonEnity>> = MutableLiveData()
    private val filteredPokemonList : MutableLiveData<MutableList<PokemonEnity>> = MutableLiveData()
//    var offset = 0
//    var hasNextItems = true
    val isShowNoResult = MutableLiveData<Boolean>()


    override fun start() {
        isShowNoResult.value = false
        allPokemonList.value = arrayListOf()
        getPokemonList()
    }

    @SuppressLint("CheckResult")
    fun getPokemonList() {
        disposable = repository.getPokemonList(0, 1500)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loadingVisibility.value = true }
            .doAfterTerminate { loadingVisibility.value = false }
            .subscribe(
                { result ->
//                    allPokemonList.value!!.addAll(result.toList())
                    allPokemonList.value = result.toMutableList()
//                    offset += 1500
//                    if(result.next.isNullOrBlank()) hasNextItems = false
                },
                { error ->
                    errorMessage.value = error.message
                    repository.getPokemonListFromLocal()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            allPokemonList.value = it.toMutableList()
                        }, {})
                }
            )
    }

    fun searchPokemons(query: String) {
        filteredPokemonList.value = allPokemonList.value?.filter { pokemon ->
            pokemon.name.contains(query, ignoreCase = true)
        }?.toMutableList()

        pokemonListDisplayed.value = filteredPokemonList.value
    }

    public override fun onCleared() {
        super.onCleared()
        disposable?.let { if (!it.isDisposed) it.dispose() }
    }
}