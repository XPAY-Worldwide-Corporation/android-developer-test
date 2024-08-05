package com.exam.myapp.di.module

import com.exam.myapp.di.ActivityScoped
import com.exam.myapp.ui.pokemon.PokemonListActivity
import com.exam.myapp.ui.pokemon.PokemonViewModelFactory
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [PokemonViewModelFactory::class])
    internal abstract fun bindPokemonListActivity(): PokemonListActivity
}
