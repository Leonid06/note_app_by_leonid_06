package com.example.leonidsnotesapplication.data.repository

import androidx.lifecycle.LiveData
import com.example.leonidsnotesapplication.data.database.NoteDao
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.domain.repository.NoteRepository

class NoteRepositoryImpl(
    private val dao : NoteDao
) : NoteRepository {
    override fun getAllNotes(): LiveData<List<Note>> {
        return dao.getAllNotes()
    }

    override suspend fun updateNoteChecked(id: Int, isChecked: Boolean) {
        dao.updateNoteChecked(id, isChecked)
    }

    override suspend fun deleteNoteById(id: Int) {
        dao.deleteNoteById(id)
    }

    override fun getNoteById(id: Int): Note {
        return dao.getNoteById(id)
    }

    override fun searchAllNotes(query: String?): LiveData<List<Note>> {
        return dao.searchAllNotes(query)
    }

    override fun searchNotesByFolder(query: String?, folder: Folder): LiveData<List<Note>> {
        return dao.searchNotesByFolderId(query, folder.id)
    }

    override fun getNotesByFolder(folder: Folder): LiveData<List<Note>> {
        return  dao.getNotesByFolderId(folder.id)
    }

    override suspend fun deleteNote(note: Note) {
        dao.updateOnDeleteNote(note.folderId)
        dao.deleteNote(note)
    }


    override suspend fun insertNote(note: Note , isNew : Boolean) {
        dao.insertNote(note)
        if(isNew){
            dao.updateOnInsertNote(note.folderId)
        }
    }

}