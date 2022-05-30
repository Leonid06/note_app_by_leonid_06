package com.example.leonidsnotesapplication.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @ColumnInfo(name = "title") val title : String?,
    @ColumnInfo(name = "content") val content : String?
    ){
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}