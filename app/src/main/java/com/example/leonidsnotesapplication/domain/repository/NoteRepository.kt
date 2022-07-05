package com.example.leonidsnotesapplication.domain.repository

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.presentation.notes_feature.util.SortOption
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotes() : Flow<List<Note>>

    fun getNotesSortedByTitle() : Flow<List<Note>>

    suspend fun updateNoteChecked(id : Int, isChecked : Boolean)

    suspend fun deleteNoteById(id : Int)

    fun getNoteById(id : Int) : Note

    fun searchAllNotes(query: String?) : Flow<List<Note>>

    fun searchNotesByFolder(query : String?, folder : Folder, option: SortOption) : Flow<List<Note>>

    fun getNotesByFolder(folder : Folder, option : SortOption) : Flow<List<Note>>

    suspend fun deleteNote(note : Note)

    suspend fun insertNote(note : Note, isNew : Boolean)
}