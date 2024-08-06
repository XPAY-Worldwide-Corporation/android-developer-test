package com.exam.myapp.data.remote

import com.exam.myapp.data.local.room.PokemonDetailEntity
import com.exam.myapp.data.repository.PokemonDetailDataSource
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonDetailRemoteDataSource @Inject constructor(
    private val pokemonDetailApi: PokemonDetailApi
): PokemonDetailDataSource {

    override fun getPokemonDetail(name: String): Single<PokemonDetailEntity> {
        return pokemonDetailApi.getPokemonDetail(name) .map { data ->
               PokemonDetailEntity(data.name, data.height, data.weight, data.experience, data.types, data.stats, data.sprites)
        }
    }

    override fun savePokemonDetailToLocal(pokemonDetail: PokemonDetailEntity) {
        TODO("Not yet implemented")
    }
}