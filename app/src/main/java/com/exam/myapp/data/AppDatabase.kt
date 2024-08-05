package com.exam.myapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.exam.myapp.data.local.room.PokemonEnity
import com.exam.myapp.data.local.room.PokemonDao

@Database(entities = [PokemonEnity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

}