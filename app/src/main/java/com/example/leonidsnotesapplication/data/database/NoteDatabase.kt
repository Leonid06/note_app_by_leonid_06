package com.example.leonidsnotesapplication.data.database

import androidx.room.*
import com.example.leonidsnotesapplication.domain.model.Note


@Database(entities = [Note::class] , version =  6)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun NoteDao() : NoteDao
}