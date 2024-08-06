package com.exam.myapp.di.module

import com.exam.myapp.data.remote.PokemonApi
import com.exam.myapp.data.remote.PokemonDetailApi
import com.exam.myapp.di.interceptor.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    private val BASE_URL = "https://pokeapi.co/api/v2/"

    private fun buildOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(NetworkConnectionInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = buildOkHttpClient()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providePokemonApi(retrofit: Retrofit): PokemonApi = retrofit.create(PokemonApi::class.java)

    @Provides
    @Singleton
    fun providePokemonDetailApi(retrofit: Retrofit): PokemonDetailApi = retrofit.create(PokemonDetailApi::class.java)


}