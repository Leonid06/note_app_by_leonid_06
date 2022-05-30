package com.example.leonidsnotesapplication.data.repository

import com.example.leonidsnotesapplication.data.database.NoteDao
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.domain.repository.NoteRepository

class NoteRepositoryImpl(
    private val dao : NoteDao
) : NoteRepository {
    override fun getAllNotes(): List<Note> {
        return dao.getAllNotes()
    }

    override suspend fun deleteNote(note: Note) {
       return dao.deleteNote(note)
    }

    override suspend fun insertNote(note: Note) {
        return dao.insertNote(note)
    }

}