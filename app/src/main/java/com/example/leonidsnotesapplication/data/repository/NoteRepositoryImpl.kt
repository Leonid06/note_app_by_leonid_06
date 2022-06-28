package com.example.leonidsnotesapplication.data.repository

import com.example.leonidsnotesapplication.data.database.NoteDao
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.domain.repository.NoteRepository

class NoteRepositoryImpl(
    private val dao : NoteDao
) : NoteRepository {
    override fun getAllNotes(): ArrayList<Note> {
        return dao.getAllNotes() as ArrayList<Note>
    }

    override fun searchAllNotes(query: String?): ArrayList<Note> {
        return dao.searchAllNotes(query) as ArrayList<Note>
    }

    override fun searchNotesByFolder(query: String?, folder: Folder): ArrayList<Note> {
        return dao.searchNotesByFolderId(query, folder.id) as ArrayList<Note>
    }

    override fun getNotesByFolder(folder: Folder): ArrayList<Note> {
        return  dao.getFolderWithNotesByFolderId(folder.id).notes as ArrayList<Note>
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