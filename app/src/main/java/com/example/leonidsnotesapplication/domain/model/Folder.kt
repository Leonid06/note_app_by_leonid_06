package com.example.leonidsnotesapplication.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Folder(
    val title : String?,
    @PrimaryKey(autoGenerate = true) val id : Int = 0
)
