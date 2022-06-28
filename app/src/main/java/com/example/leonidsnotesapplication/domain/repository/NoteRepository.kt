package com.example.leonidsnotesapplication.domain.repository

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.domain.model.Note

interface NoteRepository {
    fun getAllNotes() : ArrayList<Note>

    fun searchAllNotes(query: String?) : ArrayList<Note>

    fun searchNotesByFolder(query : String?, folder : Folder) : ArrayList<Note>

    fun getNotesByFolder(folder : Folder) : ArrayList<Note>

    suspend fun deleteNote(note : Note)

    suspend fun insertNote(note : Note, isNew : Boolean)
}