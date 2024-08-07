package com.exam.myapp.di.component

import com.exam.myapp.MyApp
import com.exam.myapp.di.module.ActivityModule
import com.exam.myapp.di.module.AppModule
import com.exam.myapp.di.module.DatabaseModule
import com.exam.myapp.di.module.NetworkModule
import com.exam.myapp.di.module.RepositoryModule
import com.exam.myapp.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityModule::class, DatabaseModule::class,
        RepositoryModule::class, NetworkModule::class, ViewModelModule::class]
)
interface AppComponent : AndroidInjector<MyApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MyApp): Builder

        fun build(): AppComponent
    }
}