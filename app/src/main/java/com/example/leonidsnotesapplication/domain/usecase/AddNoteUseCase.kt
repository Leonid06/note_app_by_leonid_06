package com.example.leonidsnotesapplication.domain.usecase

import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.domain.repository.NoteRepository

class AddNoteUseCase(
    private val noteRepository : NoteRepository
) {
    suspend fun execute(note : Note){
        noteRepository.insertNote(note)
    }
}