package com.exam.myapp.data.remote

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Pokemon(
    val name: String,
    val url: String
) {
    fun getImageUrl(): String {
        val id = url.split("/").dropLast(1).last()
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
    }

    fun name(): String = name.replaceFirstChar { it.uppercase() }
}