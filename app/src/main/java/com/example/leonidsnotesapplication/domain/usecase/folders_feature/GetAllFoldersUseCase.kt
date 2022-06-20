package com.example.leonidsnotesapplication.domain.usecase.folders_feature

import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.domain.repository.FoldersRepository
import javax.inject.Inject

class GetAllFoldersUseCase @Inject constructor(
    private val foldersRepository: FoldersRepository
) {
    fun execute() : ArrayList<Folder> {
        return ArrayList(foldersRepository.getAllFolders())
    }
}