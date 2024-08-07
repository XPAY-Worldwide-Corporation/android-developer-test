package com.exam.myapp.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Single

@Dao
interface  PokemonDao {
    @Query("SELECT * FROM `pokemon`")
    fun getPokemonList(): Single<List<PokemonEnity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPokemon(pokemon: List<PokemonEnity>)

}