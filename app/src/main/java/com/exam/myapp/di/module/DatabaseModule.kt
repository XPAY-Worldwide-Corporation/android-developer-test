package com.exam.myapp.di.module

import androidx.room.Room
import com.exam.myapp.MyApp
import com.exam.myapp.data.AppDatabase
import com.exam.myapp.data.local.room.PokemonDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: MyApp): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "pokemon.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providePokemonDao(database: AppDatabase): PokemonDao = database.pokemonDao()

}