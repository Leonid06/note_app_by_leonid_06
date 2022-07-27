package com.leonid.leonidsnotesapplication.domain.repository

import com.leonid.leonidsnotesapplication.domain.model.Folder
import com.leonid.leonidsnotesapplication.presentation.notes_feature.util.SortOption
import kotlinx.coroutines.flow.Flow

interface FoldersRepository {

    fun getAllFolders(option: SortOption) : Flow<List<Folder>>

    fun searchAllFolders(query: String, option: SortOption)  : Flow<List<Folder>>

    fun getFolderTitleById(id : Int) : String

    suspend fun deleteNotesByFolder(folder : Folder)

    suspend fun updateFolderTitle(folder : Folder, title : String)

    suspend fun addFolder(folder: Folder)

    suspend fun deleteFolder(folder: Folder)
}