package com.leonid.leonidsnotesapplication.data.database

import androidx.room.*
import com.leonid.leonidsnotesapplication.domain.model.Folder
import com.leonid.leonidsnotesapplication.domain.model.Note


@Database(entities = [Note::class, Folder::class] , version =  14 )
abstract class NoteDatabase : RoomDatabase() {
    abstract fun NoteDao() : NoteDao
}