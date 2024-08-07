package com.exam.myapp.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Single

@Dao
interface  PokemonDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonDetail(pokemonDetail: PokemonDetailEntity)

    @Query("SELECT * FROM pokemon_detail WHERE name = :name")
    fun getPokemonDetail(name: String): Single<PokemonDetailEntity>

}