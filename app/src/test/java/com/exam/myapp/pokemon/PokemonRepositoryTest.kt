package com.exam.myapp.pokemon

import com.exam.myapp.data.local.PokemonLocalDataSource
import com.exam.myapp.data.remote.PokemonRemoteDataSource
import com.exam.myapp.data.repository.PokemonRepository
import com.exam.myapp.pokemon.PokemonViewModelTest.Companion.createMockResponse
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PokemonRepositoryTest {

    private lateinit var repository: PokemonRepository
    private lateinit var localDataSource: PokemonLocalDataSource
    private lateinit var remoteDataSource: PokemonRemoteDataSource

    @Before
    fun setUp() {
        localDataSource = mock()
        remoteDataSource = mock()
        repository = PokemonRepository(localDataSource, remoteDataSource)
    }

    @Test
    fun `getPokemonList response should save to local db on success`() {
        val pokemonList = createMockResponse()
        whenever(remoteDataSource.getPokemonList(0, 1500)).thenReturn(Single.just(pokemonList))

        repository.getPokemonList(0, 1500)
            .test()
            .assertNoErrors()
            .assertValue { it == pokemonList }

        verify(localDataSource).saveAllPokemonToLocal(pokemonList)
    }

    @Test
    fun `getPokemonList do nothing on error`() {
        whenever(remoteDataSource.getPokemonList(0, 1500)).thenReturn(Single.error(Throwable("error")))

        repository.getPokemonList(0, 1500)
            .test()
            .assertError { it.message == "error" }

        verify(localDataSource, never()).saveAllPokemonToLocal(any())
    }

//    @Test
//    fun `saveAllPokemonToLocal should save data to local `() {
//        val pokemonList = createMockResponse()
//        repository.saveAllPokemonToLocal(pokemonList)
//
//        verify(localDataSource).saveAllPokemonToLocal(pokemonList)
//    }

    @Test
    fun `getPokemonListFromLocal should return list from local db`() {
        val pokemonList = createMockResponse()
        whenever(localDataSource.getPokemonList(0, 1500)).thenReturn(Single.just(pokemonList))

        repository.getPokemonListFromLocal()
            .test()
            .assertNoErrors()
            .assertValue { it == pokemonList }
    }
}
