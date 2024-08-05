package com.exam.myapp.data.local

import com.exam.myapp.data.local.room.PokemonDao
import com.exam.myapp.data.local.room.PokemonEnity
import com.exam.myapp.data.repository.PokemonDataSource
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonLocalDataSource @Inject constructor(
    private val pokemonDao: PokemonDao
): PokemonDataSource {

    override fun getPokemonList(offset: Int, limit: Int): Single<List<PokemonEnity>> {
        return pokemonDao.getPokemonList()
    }

    override fun saveAllPokemonToLocal(data: List<PokemonEnity>) {
        pokemonDao.insertAllPokemon(data)
    }
}