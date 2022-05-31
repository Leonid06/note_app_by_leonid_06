package com.example.leonidsnotesapplication.domain.repository

import androidx.lifecycle.LiveData
import com.example.leonidsnotesapplication.domain.model.Note

interface NoteRepository {
    fun getAllNotes() : ArrayList<Note>

    suspend fun deleteNote(note : Note)

    suspend fun insertNote(note : Note)
}