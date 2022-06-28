package com.example.leonidsnotesapplication.domain.repository

import com.example.leonidsnotesapplication.domain.model.Folder

interface FoldersRepository {

    fun getAllFolders() : List<Folder>

    suspend fun updateFolderTitle(folder : Folder, title : String)

    suspend fun addFolder(folder: Folder)

    suspend fun deleteFolder(folder: Folder)
}