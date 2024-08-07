package com.exam.myapp.data.local.room

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.exam.myapp.data.remote.PokeType
import com.exam.myapp.data.remote.Sprites
import com.exam.myapp.data.remote.Stat

@Entity(tableName = "pokemon_detail", indices = [Index(value = ["name"], unique = true)])
data class PokemonDetailEntity(
    var name: String = "",
    var height: Int = 0,
    var weight: Int = 0,
    var experience: Int = 0,
    val types: List<PokeType>,
    val stats: List<Stat>,
    val sprites: Sprites
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    fun getNameString(): String = name.replaceFirstChar { it.uppercase() }
    fun getWeightString(): String = String.format("%.1f KG", weight.toFloat() / 10)
    fun getHeightString(): String = "${height * 10} CM"
    fun getTypesList(): String {
        val typesString = mutableListOf<String>()
        for(type in types) {
            typesString.add(type.type.name)
        }
        return typesString.joinToString(separator = ", ") { word ->
            word.replaceFirstChar { it.uppercase() }
        }
    }

    fun getStatsByName(statName: String): Int {
        return stats.firstOrNull { it.stat.name == statName }?.baseStat!!
    }

    fun getStatsTextByName(statName: String): String {
        return stats.firstOrNull { it.stat.name == statName }?.baseStat.toString() + "/270"
    }

    fun getExperiencePercent(): String {
        return "$experience/1000"
    }
}