package com.example.leonidsnotesapplication.presentation.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.leonidsnotesapplication.data.database.DatabaseCallback
import com.example.leonidsnotesapplication.data.database.NoteDao
import com.example.leonidsnotesapplication.data.database.NoteDatabase
import com.example.leonidsnotesapplication.data.repository.FoldersRepositoryImpl
import com.example.leonidsnotesapplication.data.repository.NoteRepositoryImpl
import com.example.leonidsnotesapplication.domain.repository.FoldersRepository
import com.example.leonidsnotesapplication.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideNoteRepository(dao : NoteDao) : NoteRepository {
        return NoteRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideFoldersRepository(dao : NoteDao) : FoldersRepository {
        return FoldersRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideNoteDao(noteDatabase: NoteDatabase) : NoteDao{
        return noteDatabase.NoteDao()
    }

    @Provides
    @Singleton
    fun provideNoteDatabase(app : Application) : NoteDatabase{
        return Room.databaseBuilder(
            app ,
            NoteDatabase::class.java,
            "notes",

        ).fallbackToDestructiveMigration()
            .addCallback(DatabaseCallback())
            .build()
    }
}