package com.exam.myapp.di.module

import com.exam.myapp.data.local.PokemonDetailLocalDataSource
import com.exam.myapp.data.local.PokemonLocalDataSource
import com.exam.myapp.data.remote.PokemonDetailRemoteDataSource
import com.exam.myapp.data.remote.PokemonRemoteDataSource
import com.exam.myapp.data.repository.PokemonDataSource
import com.exam.myapp.di.qualifier.LocalData
import com.exam.myapp.di.qualifier.RemoteData
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    @LocalData
    abstract fun bindPokemonLocalDataSource(pokemonLocalDataSource: PokemonLocalDataSource): PokemonDataSource

    @Singleton
    @Binds
    @RemoteData
    abstract fun bindPokemonRemoteDataSource(pokemonRemoteDataSource: PokemonRemoteDataSource): PokemonDataSource

    @Singleton
    @Binds
    @RemoteData
    abstract fun bindPokemonDetailRemoteDataSource(pokemonDetailRemoteDataSource: PokemonDetailRemoteDataSource): PokemonDetailRemoteDataSource

    @Singleton
    @Binds
    @LocalData
    abstract fun bindPokemonDetailLocalDataSource(pokemonDetailRemoteDataSource: PokemonDetailLocalDataSource): PokemonDetailLocalDataSource

}