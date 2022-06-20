package com.example.leonidsnotesapplication.presentation.di

import com.example.leonidsnotesapplication.domain.repository.FoldersRepository
import com.example.leonidsnotesapplication.domain.repository.NoteRepository
import com.example.leonidsnotesapplication.domain.usecase.folders_feature.AddFolderUseCase
import com.example.leonidsnotesapplication.domain.usecase.folders_feature.GetAllFoldersUseCase
import com.example.leonidsnotesapplication.domain.usecase.notes_feature.AddNoteUseCase
import com.example.leonidsnotesapplication.domain.usecase.notes_feature.DeleteNoteUseCase
import com.example.leonidsnotesapplication.domain.usecase.notes_feature.GetAllNotesUseCase
import com.example.leonidsnotesapplication.domain.usecase.notes_feature.SearchNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideAddNoteUseCase(noteRepository: NoteRepository): AddNoteUseCase {
        return AddNoteUseCase(noteRepository)
    }

    @Provides
    fun provideAddFolderUseCase(foldersRepository: FoldersRepository) : AddFolderUseCase {
        return AddFolderUseCase(foldersRepository)
    }

    @Provides
    fun provideDeleteNoteUseCase(noteRepository: NoteRepository): DeleteNoteUseCase {
        return DeleteNoteUseCase(noteRepository)
    }

    @Provides
    fun provideGetAllNotesUseCase(noteRepository: NoteRepository): GetAllNotesUseCase {
        return GetAllNotesUseCase(noteRepository)
    }

    @Provides
    fun provideSearchNotesUseCase(noteRepository: NoteRepository) : SearchNoteUseCase {
        return  SearchNoteUseCase(noteRepository)
    }

    @Provides
    fun provideGetAllFoldersUseCase(foldersRepository: FoldersRepository) : GetAllFoldersUseCase {
        return GetAllFoldersUseCase(foldersRepository)
    }

}