package com.exam.myapp.data.remote

import com.exam.myapp.data.local.room.PokemonEnity
import com.exam.myapp.data.repository.PokemonDataSource
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRemoteDataSource @Inject constructor(
    private val pokemonApi: PokemonApi
): PokemonDataSource {

    override fun getPokemonList(offset: Int, limit: Int): Single<List<PokemonEnity>> {
       return pokemonApi.getPokemonList(offset, limit).map { data ->
           data.results.map {
                PokemonEnity(it.name, it.url, it.getImageUrl())
            }
        }
    }

    override fun saveAllPokemonToLocal(data: List<PokemonEnity>) {
        TODO("Not yet implemented")
    }
}