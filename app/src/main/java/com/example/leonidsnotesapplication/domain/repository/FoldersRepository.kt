package com.example.leonidsnotesapplication.domain.repository

import androidx.lifecycle.LiveData
import com.example.leonidsnotesapplication.domain.model.Folder

interface FoldersRepository {

    fun getAllFolders() : LiveData<List<Folder>>

    fun getFolderTitleById(id : Int) : String

    suspend fun updateFolderTitle(folder : Folder, title : String)

    suspend fun addFolder(folder: Folder)

    suspend fun deleteFolder(folder: Folder)
}