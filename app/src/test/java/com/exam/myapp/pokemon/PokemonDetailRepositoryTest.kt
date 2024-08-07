package com.exam.myapp.pokemon

import com.exam.myapp.data.local.PokemonDetailLocalDataSource
import com.exam.myapp.data.local.room.PokemonDetailEntity
import com.exam.myapp.data.remote.PokemonDetailRemoteDataSource
import com.exam.myapp.data.repository.PokemonDetailRepository
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PokemonDetailRepositoryTest {

    private lateinit var repository: PokemonDetailRepository
    private lateinit var localDataSource: PokemonDetailLocalDataSource
    private lateinit var remoteDataSource: PokemonDetailRemoteDataSource

    @Before
    fun setUp() {
        localDataSource = mock()
        remoteDataSource = mock()
        repository = PokemonDetailRepository(localDataSource, remoteDataSource)
    }

    @Test
    fun `getPokemonDetail should fetch from remote and save to local on success`() {
        val name = "pikachu"
        val entity = createPokemonDetailMock(name)
        whenever(remoteDataSource.getPokemonDetail(name)).thenReturn(Single.just(entity))

        repository.getPokemonDetail(name)
            .test()
            .assertNoErrors()
            .assertValue { it == entity }

        verify(localDataSource).savePokemonDetailToLocal(entity)
    }

    @Test
    fun `getPokemonDetail should return throwable on error`() {
        whenever(remoteDataSource.getPokemonDetail("pikachu")).thenReturn(Single.error(Throwable("error")))

        repository.getPokemonDetail("pikachu")
            .test()
            .assertError { it.message == "error" }

        verify(localDataSource, never()).savePokemonDetailToLocal(any())
    }

    @Test
    fun `savePokemonDetailToLocal should save data to local`() {
        val name = "pikachu"
        val entity = createPokemonDetailMock(name)

        repository.savePokemonDetailToLocal(entity)
        verify(localDataSource).savePokemonDetailToLocal(entity)
    }

    @Test
    fun `getPokemonDetailFromLocal should fetch data from local`() {
        val name = "pikachu"
        val entity = createPokemonDetailMock(name)

        whenever(localDataSource.getPokemonDetail(name)).thenReturn(Single.just(entity))

        repository.getPokemonDetailFromLocal(name)
            .test()
            .assertComplete()
            .assertValue { it == entity }
    }

    companion object {
        fun createPokemonDetailMock(name: String): PokemonDetailEntity {
            return PokemonDetailEntity(name, 1, 1, 0, mock(), mock(), mock())
        }
    }
}