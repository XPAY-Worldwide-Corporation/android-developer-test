package com.exam.myapp.data.local.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon", indices = [Index(value = ["name"], unique = true)])
data class PokemonEnity (
    var name: String = "",
    var url: String = "",
    @ColumnInfo(name = "img_url")
    var imgUrl: String = "",
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}