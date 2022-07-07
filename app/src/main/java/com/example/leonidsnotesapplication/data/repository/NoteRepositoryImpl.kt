package com.example.leonidsnotesapplication.data.repository

import com.example.leonidsnotesapplication.data.database.NoteDao
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.domain.repository.NoteRepository
import com.example.leonidsnotesapplication.presentation.notes_feature.util.SortOption
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao : NoteDao
) : NoteRepository {
    override fun getAllNotes(option: SortOption): Flow<List<Note>> {
        return when(option){
            SortOption.ByDate -> dao.getAllNotes()
            SortOption.ByTitle -> dao.getAllNotesSortedByTitle()
        }
    }

    override suspend fun changeNoteFolder(note: Note, folder: Folder) {
        dao.updateOnDeleteNote(note.folderId)
        dao.changeNoteFolder(note.id, folder.id)
        dao.updateOnInsertNote(folder.id)
    }

    override fun getNotesSortedByTitle(): Flow<List<Note>> {
        return dao.getNotesSortedByTitle()
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

    override fun searchAllNotes(query: String, option: SortOption): Flow<List<Note>> {
        return when(option){
            SortOption.ByDate -> dao.searchAllNotes(query)
            SortOption.ByTitle -> dao.searchAllNotesSortedByTitle(query)
        }
    }

    override fun searchNotesByFolder(query: String?, folder: Folder, option: SortOption): Flow<List<Note>> {
        return when(option){
            SortOption.ByDate -> dao.searchNotesByFolderId(query, folder.id)
            SortOption.ByTitle -> dao.searchNotesByFolderIdSortByTitle(query, folder.id)
        }
    }

    override fun getNotesByFolder(folder: Folder, option: SortOption): Flow<List<Note>> {
        return when(option){
            SortOption.ByDate -> {
                dao.getNotesByFolderId(folder.id)
            }
            SortOption.ByTitle -> {
                dao.getNotesByFolderIdSortedByTitle(folder.id)
            }
        }
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