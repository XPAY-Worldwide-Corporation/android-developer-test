package com.exam.myapp.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.exam.myapp.di.ViewModelKey
import com.exam.myapp.ui.pokemon.PokemonViewModel
import com.exam.myapp.util.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton


@Module(includes = [AppModule::class, RepositoryModule::class])
abstract class ViewModelModule {


    @Singleton
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(PokemonViewModel::class)
    abstract fun bindPokemonViewModel(viewModel: PokemonViewModel): ViewModel


}