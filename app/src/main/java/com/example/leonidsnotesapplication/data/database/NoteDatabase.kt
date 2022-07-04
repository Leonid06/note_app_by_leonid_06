package com.example.leonidsnotesapplication.data.database

import androidx.room.*
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.domain.model.Note


@Database(entities = [Note::class, Folder::class] , version =  11 )
abstract class NoteDatabase : RoomDatabase() {
    abstract fun NoteDao() : NoteDao
}