package com.exam.myapp.pokemon

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.exam.myapp.data.local.room.PokemonEnity
import com.exam.myapp.data.repository.PokemonRepository
import com.exam.myapp.ui.pokemon.PokemonViewModel
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

class PokemonViewModelTest {

    @get:Rule
    val defaultExecutor = InstantTaskExecutorRule()

    private lateinit var repository: PokemonRepository
    private lateinit var viewModel: PokemonViewModel

    private val observerAllPokemonList: Observer<MutableList<PokemonEnity>> = mock<Observer<MutableList<PokemonEnity>>>()
    private val observerPokemonList: Observer<MutableList<PokemonEnity>> = mock<Observer<MutableList<PokemonEnity>>>()
    private val observerErrorMessage: Observer<String> = mock<Observer<String>>()
    private val observerIsShowNoResult: Observer<Boolean> = mock<Observer<Boolean>>()

    @Before
    fun setUp() {
        repository = mock()
        viewModel = PokemonViewModel(repository)

        viewModel.allPokemonList.observeForever(observerAllPokemonList)
        viewModel.pokemonListDisplayed.observeForever(observerPokemonList)
        viewModel.errorMessage.observeForever(observerErrorMessage)
        viewModel.isShowNoResult.observeForever(observerIsShowNoResult)

        //Schedulers.trampoline() executes the tasks on the current thread synchronously.
        RxAndroidPlugins.setInitMainThreadSchedulerHandler{ Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @After
    fun tearDown() {
        RxJavaPlugins.reset()
    }

    @Test
    fun `start method should call getPokemonList and initialize value of allPokomenList and set isShowNoResult to false`() {
        val mockResp = createMockResponse()
        whenever(repository.getPokemonList(0, 1500)).thenReturn(Single.just(mockResp))

        viewModel.start()

        verify(observerAllPokemonList).onChanged(arrayListOf())
        verify(observerIsShowNoResult).onChanged(false)
        verify(repository).getPokemonList(0, 1500)
    }

    @Test
    fun `getPokemonList should update allPokemonList on success`() {
        val mockResp = createMockResponse()
        whenever(repository.getPokemonList(0, 1500)).thenReturn(Single.just(mockResp))

        viewModel.getPokemonList()

        assertEquals(mockResp, viewModel.allPokemonList.value)
    }

    @Test
    fun `getPokemonList should update errorMessage and fallback to local data on error`() {
        whenever(repository.getPokemonList(0, 1500)).thenReturn(Single.error(Throwable("Error")))

        val mockResp = createMockResponse()
        whenever(repository.getPokemonListFromLocal()).thenReturn(Single.just(mockResp))

        viewModel.getPokemonList()

        assertEquals("Error", viewModel.errorMessage.value)
        assertEquals(mockResp, viewModel.allPokemonList.value)
    }

    @Test
    fun `searchPokemon with result should update pokemonListDisplayed`() {
        val mockResp = createMockResponse()
        viewModel.allPokemonList.postValue(mockResp.toMutableList())
        viewModel.searchPokemons("pik")

        assertEquals(mockResp, viewModel.pokemonListDisplayed.value)
    }

    @Test
    fun `searchPokemon with no result should update pokemonListDisplayed to empty`() {
        val mockResp = createMockResponse()
        viewModel.allPokemonList.postValue(mockResp.toMutableList())

        viewModel.searchPokemons("abc")

        assertEquals(true, viewModel.pokemonListDisplayed.value?.isEmpty())
    }

    @Test
    fun `onCleared should dispose the Disposable`() {
        val disposable =  mock<Disposable>()
        viewModel.disposable = disposable
        whenever(disposable.isDisposed).thenReturn(false)

        viewModel.onCleared()

        verify(disposable).dispose()
    }

    companion object {
        fun createMockResponse(): List<PokemonEnity> {
            val pokemonListMock = mutableListOf<PokemonEnity>()
            pokemonListMock.add(PokemonEnity("pikachu", "https://pokeapi.co/api/v2/pokemon/1/", "https://pokeapi.co/api/v2/pokemon/1/"))
            return pokemonListMock
        }
    }

}