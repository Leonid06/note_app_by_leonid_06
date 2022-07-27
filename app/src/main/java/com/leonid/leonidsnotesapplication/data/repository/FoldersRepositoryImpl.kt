package com.leonid.leonidsnotesapplication.data.repository

import com.leonid.leonidsnotesapplication.data.database.NoteDao
import com.leonid.leonidsnotesapplication.domain.model.Folder
import com.leonid.leonidsnotesapplication.domain.repository.FoldersRepository
import com.leonid.leonidsnotesapplication.presentation.notes_feature.util.SortOption
import kotlinx.coroutines.flow.Flow

class FoldersRepositoryImpl(
    private val dao : NoteDao
) : FoldersRepository {
    override fun getAllFolders(option: SortOption): Flow<List<Folder>> {
        return when(option){
            SortOption.ByDate -> dao.getAllFolders()
            SortOption.ByTitle -> dao.getAllFoldersSortedByTitle()
        }
    }

    override fun searchAllFolders(query: String, option: SortOption): Flow<List<Folder>> {
        return when(option){
            SortOption.ByDate -> dao.searchAllFolders(query)
            SortOption.ByTitle -> dao.searchAllFoldersSortedByTitle(query)
        }
    }

    override fun getFolderTitleById(id : Int): String {
        return dao.getFolderTitleById(id)
    }

    override suspend fun deleteNotesByFolder(folder: Folder) {
        dao.deleteNotesByFolderId(folder.id)
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