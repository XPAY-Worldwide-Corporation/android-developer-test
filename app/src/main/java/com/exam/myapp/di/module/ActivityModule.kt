package com.exam.myapp.di.module

import com.exam.myapp.di.ActivityScoped
import com.exam.myapp.ui.pokemon.PokemonListActivity
import com.exam.myapp.ui.pokemon.PokemonModule
import com.exam.myapp.ui.pokemon_detail.PokemonDetailActivity
import com.exam.myapp.ui.pokemon_detail.PokemonDetailModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [PokemonModule::class])
    internal abstract fun bindPokemonListActivity(): PokemonListActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [PokemonDetailModule::class])
    internal abstract fun bindPokemonDetailActivity(): PokemonDetailActivity
}
