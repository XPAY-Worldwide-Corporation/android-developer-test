package com.exam.myapp.pokemon

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.exam.myapp.data.local.room.PokemonDetailEntity
import com.exam.myapp.data.repository.PokemonDetailRepository
import com.exam.myapp.ui.pokemon_detail.PokemonDetailViewModel
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PokemonDetailViewModelTest {

    @get:Rule
    val defaultExecutor = InstantTaskExecutorRule()

    private lateinit var repository: PokemonDetailRepository
    private lateinit var viewModel: PokemonDetailViewModel

    private val observerIsBack: Observer<Boolean> = mock<Observer<Boolean>>()
    private val observerPokemonDetail: Observer<PokemonDetailEntity> = mock<Observer<PokemonDetailEntity>>()
    private val observerErrorMessage: Observer<String> = mock<Observer<String>>()

    @Before
    fun setUp() {
        repository = mock()
        viewModel = PokemonDetailViewModel(repository)

        viewModel.isBack.observeForever(observerIsBack)
        viewModel.pokemonDetail.observeForever(observerPokemonDetail)
        viewModel.errorMessage.observeForever(observerErrorMessage)

        RxAndroidPlugins.setInitMainThreadSchedulerHandler{ Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @After
    fun tearDown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    @Test
    fun `start method should call getPokemonDetail`() {
        val name = "pikachu"
        val entity = PokemonDetailRepositoryTest.createPokemonDetailMock(name)
        viewModel.pokemonName = name

        whenever(repository.getPokemonDetail(name)).thenReturn(Single.just(entity))

        viewModel.start()

        verify(repository).getPokemonDetail(name)
    }

    @Test
    fun `getPokemonDetailByName should update pokemonDetail on success`() {
        val name = "pikachu"
        val entity = PokemonDetailRepositoryTest.createPokemonDetailMock(name)
        whenever(repository.getPokemonDetail(name)).thenReturn(Single.just(entity))

        viewModel.getPokemonDetailByName(name)

        assertEquals(entity, viewModel.pokemonDetail.value)
    }

    @Test
    fun `getPokemonDetailByName should update errorMessage and fallback to local data on error`() {
        whenever(repository.getPokemonDetail("pikachu")).thenReturn(Single.error(Throwable("Error")))

        val entity = PokemonDetailRepositoryTest.createPokemonDetailMock("pikachu")
        whenever(repository.getPokemonDetailFromLocal("pikachu")).thenReturn(Single.just(entity))

        viewModel.getPokemonDetailByName("pikachu")

        assertEquals("Error", viewModel.errorMessage.value)
        assertEquals(entity, viewModel.pokemonDetail.value)
    }

    @Test
    fun `onBack should  change value isBack to true`() {
        viewModel.onBack()
        verify(observerIsBack).onChanged(true)
    }

    @Test
    fun `onCleared should dispose the Disposable`() {
        val disposable = mock<Disposable>()

        whenever(disposable.isDisposed).thenReturn(false)

        viewModel.disposable = disposable
        viewModel.onCleared()

        verify(disposable).dispose()
    }

}