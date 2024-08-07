package com.exam.myapp.data.repository

import com.exam.myapp.data.local.room.PokemonEnity
import io.reactivex.rxjava3.core.Single

interface PokemonDataSource {

    fun getPokemonList(offset: Int, limit: Int): Single<List<PokemonEnity>>

    fun saveAllPokemonToLocal(data: List<PokemonEnity>)
}