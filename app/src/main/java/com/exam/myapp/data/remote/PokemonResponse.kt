package com.exam.myapp.data.remote

import java.io.Serializable

data class PokemonResponse(
    val count: Int,
    val next: String?,
    val results: List<PokemonResult>
): Serializable