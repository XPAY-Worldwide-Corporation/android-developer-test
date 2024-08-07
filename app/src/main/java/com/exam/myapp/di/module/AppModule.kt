package com.exam.myapp.di.module

import android.app.Application
import android.content.Context
import com.exam.myapp.MyApp
import com.exam.myapp.di.qualifier.ApplicationContext
import dagger.Binds
import dagger.Module


@Module
abstract class AppModule {

    @Binds
    @ApplicationContext
    abstract fun bindApplicationContext(application: MyApp): Context

    @Binds
    abstract fun bindApplication(application: MyApp): Application
}