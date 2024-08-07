package com.exam.myapp.data.local

import com.exam.myapp.data.local.room.PokemonDetailDao
import com.exam.myapp.data.local.room.PokemonDetailEntity
import com.exam.myapp.data.repository.PokemonDetailDataSource
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonDetailLocalDataSource @Inject constructor(
    private val pokemonDao: PokemonDetailDao
): PokemonDetailDataSource {
    override fun getPokemonDetail(name: String): Single<PokemonDetailEntity> {
        return pokemonDao.getPokemonDetail(name)
    }

    override fun savePokemonDetailToLocal(pokemonDetail: PokemonDetailEntity) {
        pokemonDao.insertPokemonDetail(pokemonDetail)
    }

}