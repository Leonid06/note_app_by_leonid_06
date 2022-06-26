package com.example.leonidsnotesapplication.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Folder(
    val title : String?,
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val noteCount : Int = 0
) : Parcelable
