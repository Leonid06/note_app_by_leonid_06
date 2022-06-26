package com.example.leonidsnotesapplication.data.repository

import com.example.leonidsnotesapplication.data.database.NoteDao
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.domain.repository.FoldersRepository

class FoldersRepositoryImpl(
    private val dao : NoteDao
) : FoldersRepository {
    override fun getAllFolders(): List<Folder> {
        return dao.getAllFolders()
    }

    override suspend fun addFolder(folder: Folder) {
        dao.insertFolder(folder)
    }

    override suspend fun deleteFolder(folder: Folder) {
        dao.deleteFolder(folder)
    }
}