package com.example.leonidsnotesapplication.domain.usecase.folders_feature

import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.domain.repository.FoldersRepository
import javax.inject.Inject

class AddFolderUseCase @Inject constructor(
    private val foldersRepository: FoldersRepository
) {
    suspend fun execute(folder : Folder){
        foldersRepository.addFolder(folder)
    }
}