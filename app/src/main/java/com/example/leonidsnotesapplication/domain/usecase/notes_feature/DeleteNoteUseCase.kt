package com.example.leonidsnotesapplication.domain.usecase.notes_feature

import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.domain.repository.NoteRepository

class DeleteNoteUseCase(
    private val noteRepository : NoteRepository
) {
    suspend fun execute(note : Note){
        noteRepository.deleteNote(note)
    }
}