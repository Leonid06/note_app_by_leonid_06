package com.example.leonidsnotesapplication.domain.model

data class NoteViewData(
    val id : Int,
    val title : String,
    val subtitle : String,
    var isStarred : Boolean,
    val folderTitle : String,
    val defaultFolder : Boolean
)