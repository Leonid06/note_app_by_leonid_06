package com.example.leonidsnotesapplication.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.leonidsnotesapplication.domain.model.Note
import dagger.Provides

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note")
    fun getAllNotes() : List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note : Note)

    @Delete
    suspend fun deleteNote(note : Note)
}