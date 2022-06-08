package com.example.leonidsnotesapplication.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Note(
    @ColumnInfo(name = "title") val title : String?,
    @ColumnInfo(name = "subtitle") val subtitle : String?,
    @ColumnInfo(name = "content") val content : String?,
    @ColumnInfo(name = "datetime") val datetime : String?,
    @PrimaryKey(autoGenerate = true) var id : Int = 0
    ) : Parcelable