package com.exam.myapp.util

import androidx.room.TypeConverter
import com.exam.myapp.data.remote.PokeType
import com.exam.myapp.data.remote.Sprites
import com.exam.myapp.data.remote.Stat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoomConverter {
    @TypeConverter
    fun convertPokeTypeToString(objects: PokeType?): String {
        return Gson().toJson(objects)
    }

    @TypeConverter
    fun fromTypesList(types: List<PokeType>): String {
        return Gson().toJson(types)
    }

    @TypeConverter
    fun toTypesList(typesString: String): List<PokeType> {
        val listType = object : TypeToken<List<PokeType>>() {}.type
        return Gson().fromJson(typesString, listType)
    }

    @TypeConverter
    fun fromSprites(sprites: Sprites): String {
        return Gson().toJson(sprites)
    }

    @TypeConverter
    fun toSprites(spriteString: String): Sprites {
        val sprites = object : TypeToken<Sprites>() {}.type
        return Gson().fromJson(spriteString, sprites)
    }

    @TypeConverter
    fun fromStats(stats: List<Stat>): String {
        return Gson().toJson(stats)
    }

    @TypeConverter
    fun toStats(statsString: String): List<Stat> {
        val stats = object : TypeToken<List<Stat>>() {}.type
        return Gson().fromJson(statsString, stats)
    }


}