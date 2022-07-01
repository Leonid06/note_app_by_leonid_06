package com.example.leonidsnotesapplication.data.repository

import androidx.lifecycle.LiveData
import com.example.leonidsnotesapplication.data.database.NoteDao
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.domain.repository.FoldersRepository

class FoldersRepositoryImpl(
    private val dao : NoteDao
) : FoldersRepository {
    override fun getAllFolders(): LiveData<List<Folder>> {
        return dao.getAllFolders()
    }

    override fun getFolderTitleById(id : Int): String {
        return dao.getFolderTitleById(id)
    }

    override suspend fun updateFolderTitle(folder: Folder, title: String) {
        dao.updateFolderTitle(folder.id, title)
    }

    override suspend fun addFolder(folder: Folder) {
        dao.insertFolder(folder)
    }

    override suspend fun deleteFolder(folder: Folder) {
        dao.deleteFolder(folder)
    }
}