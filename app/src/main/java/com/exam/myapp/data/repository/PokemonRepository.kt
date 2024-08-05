package com.exam.myapp.data.repository

import com.exam.myapp.data.local.room.PokemonEnity
import com.exam.myapp.di.qualifier.LocalData
import com.exam.myapp.di.qualifier.RemoteData
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    @LocalData private val localDataSource: PokemonDataSource,
    @RemoteData private val remoteDataSource: PokemonDataSource
) : PokemonDataSource {

    override fun getPokemonList(offset: Int, limit: Int): Single<List<PokemonEnity>> {
        return remoteDataSource.getPokemonList(offset, limit)
            .doOnSuccess { resp ->
                localDataSource.saveAllPokemonToLocal(resp)
            }
    }

    override fun saveAllPokemonToLocal(data: List<PokemonEnity>) {
        return localDataSource.saveAllPokemonToLocal(data)
    }

    fun getPokemonListFromLocal(): Single<List<PokemonEnity>> {
       return localDataSource.getPokemonList(0,1500)

    }

}