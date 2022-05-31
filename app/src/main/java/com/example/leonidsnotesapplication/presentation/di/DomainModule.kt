package com.example.leonidsnotesapplication.presentation.di

import com.example.leonidsnotesapplication.domain.repository.NoteRepository
import com.example.leonidsnotesapplication.domain.usecase.AddNoteUseCase
import com.example.leonidsnotesapplication.domain.usecase.DeleteNoteUseCase
import com.example.leonidsnotesapplication.domain.usecase.GetAllNotesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideAddNoteUseCase(userRepository: NoteRepository): AddNoteUseCase {
        return AddNoteUseCase(userRepository)
    }

    @Provides
    fun provideDeleteNoteUseCase(userRepository: NoteRepository): DeleteNoteUseCase {
        return DeleteNoteUseCase(userRepository)
    }

    @Provides
    fun provideGetAllNotesUseCase(userRepository: NoteRepository): GetAllNotesUseCase {
        return GetAllNotesUseCase(userRepository)
    }

}