package com.exam.myapp.data.remote

import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon")
    fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int) : Single<PokemonResponse>

    @GET("pokemon/{name}")
    fun getPokemonDetails(@Path("name") name: String) : Call<PokemonResponse>
}