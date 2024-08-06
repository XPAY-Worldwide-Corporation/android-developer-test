package com.exam.myapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.exam.myapp.data.local.room.PokemonEnity
import com.exam.myapp.data.local.room.PokemonDao
import com.exam.myapp.data.local.room.PokemonDetailDao
import com.exam.myapp.data.local.room.PokemonDetailEntity
import com.exam.myapp.util.RoomConverter

@Database(entities = [PokemonEnity::class, PokemonDetailEntity::class], version = 1)
@TypeConverters(RoomConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao
    abstract fun pokemonDetailDao(): PokemonDetailDao

}