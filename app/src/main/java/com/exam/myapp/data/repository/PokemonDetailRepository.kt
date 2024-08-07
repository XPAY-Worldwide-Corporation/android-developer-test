package com.exam.myapp.data.repository

import com.exam.myapp.data.local.PokemonDetailLocalDataSource
import com.exam.myapp.data.local.room.PokemonDetailEntity
import com.exam.myapp.data.remote.PokemonDetailRemoteDataSource
import com.exam.myapp.di.qualifier.LocalData
import com.exam.myapp.di.qualifier.RemoteData
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonDetailRepository @Inject constructor(
    @LocalData private val localDataSource: PokemonDetailLocalDataSource,
    @RemoteData private val remoteDataSource: PokemonDetailRemoteDataSource
) : PokemonDetailDataSource {

    override fun getPokemonDetail(name: String): Single<PokemonDetailEntity> {
        return remoteDataSource.getPokemonDetail(name)
            .doOnSuccess { resp ->
                localDataSource.savePokemonDetailToLocal(resp)
            }
    }

    override fun savePokemonDetailToLocal(pokemonDetail: PokemonDetailEntity) {
        localDataSource.savePokemonDetailToLocal(pokemonDetail)
    }

    fun getPokemonDetailFromLocal(name: String): Single<PokemonDetailEntity> {
        return localDataSource.getPokemonDetail(name)
    }

}