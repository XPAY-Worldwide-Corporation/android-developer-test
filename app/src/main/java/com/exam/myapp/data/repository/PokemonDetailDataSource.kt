package com.exam.myapp.data.repository

import com.exam.myapp.data.local.room.PokemonDetailEntity
import io.reactivex.rxjava3.core.Single

interface PokemonDetailDataSource {

    fun getPokemonDetail(name: String): Single<PokemonDetailEntity>

    fun savePokemonDetailToLocal(pokemonDetail: PokemonDetailEntity)

}