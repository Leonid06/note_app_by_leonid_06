package com.example.leonidsnotesapplication.domain.usecase

import androidx.lifecycle.LiveData
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.domain.repository.NoteRepository

class GetAllNotesUseCase(
    private val repository: NoteRepository
) {
    fun execute() : ArrayList<Note> {
        return repository.getAllNotes()
    }
}