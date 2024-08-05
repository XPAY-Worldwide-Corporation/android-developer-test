package com.exam.myapp.data.remote

data class PokemonResult(
    val name: String,
    val url: String
) {
    fun getImageUrl(): String {
        val id = url.split("/").dropLast(1).last()
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
    }
}

