package com.example.leonidsnotesapplication.presentation.di

import com.example.leonidsnotesapplication.domain.repository.NoteRepository
import com.example.leonidsnotesapplication.domain.usecase.AddNoteUseCase
import com.example.leonidsnotesapplication.domain.usecase.DeleteNoteUseCase
import com.example.leonidsnotesapplication.domain.usecase.GetAllNotesUseCase
import com.example.leonidsnotesapplication.domain.usecase.SearchNoteUseCase
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
    fun provideDeleteNoteUseCase(noteRepository: NoteRepository): DeleteNoteUseCase {
        return DeleteNoteUseCase(noteRepository)
    }

    @Provides
    fun provideGetAllNotesUseCase(noteRepository: NoteRepository): GetAllNotesUseCase {
        return GetAllNotesUseCase(noteRepository)
    }

    @Provides
    fun provideSearchNotesUseCase(noteRepository: NoteRepository) : SearchNoteUseCase{
        return  SearchNoteUseCase(noteRepository)
    }

}