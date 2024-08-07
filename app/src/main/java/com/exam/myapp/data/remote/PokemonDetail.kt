package com.exam.myapp.data.remote

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokemonDetail(
    val name: String,
    val height: Int,
    val weight: Int,
    @SerializedName("base_experience") val experience: Int,
    val sprites: Sprites,
    val types: List<PokeType>,
    val stats: List<Stat>
): Serializable

data class Sprites(
    @SerializedName("front_default")
    val frontDefault: String
)

data class Stat(
    @SerializedName("base_stat")
    val baseStat: Int,
    val stat: StatDetails
)

data class StatDetails(
    val name: String
)

data class PokeType(
    val type: PokeTypeDetail
): Serializable

data class PokeTypeDetail(
    val name: String
): Serializable