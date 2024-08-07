package com.exam.myapp.data.remote

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonDetailApi {

    @GET("pokemon/{name}")
    fun getPokemonDetail(@Path("name") name: String) : Single<PokemonDetail>
}