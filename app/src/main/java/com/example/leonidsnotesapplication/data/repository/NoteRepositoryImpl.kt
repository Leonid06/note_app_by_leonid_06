package com.example.leonidsnotesapplication.data.repository

import com.example.leonidsnotesapplication.data.database.NoteDao
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.domain.repository.NoteRepository

class NoteRepositoryImpl(
    private val dao : NoteDao
) : NoteRepository {
    override fun getAllNotes(): ArrayList<Note> {
        return ArrayList(dao.getAllNotes())
    }

    override fun searchAllNotes(query: String?): ArrayList<Note> {
        return ArrayList(dao.searchAllNotes(query))
    }

    override fun searchNotesByFolder(query: String?, folder: Folder): ArrayList<Note> {
        return ArrayList(dao.searchNotesByFolderId(query, folder.id))
    }

    override fun getNotesByFolder(folder: Folder): ArrayList<Note> {
        return  dao.getFolderWithNotesByFolderId(folder.id).notes as ArrayList<Note>
    }

    override suspend fun deleteNote(note: Note) {
       return dao.deleteNote(note)
    }

    override suspend fun insertNote(note: Note) {
        return dao.insertNote(note)
    }

}