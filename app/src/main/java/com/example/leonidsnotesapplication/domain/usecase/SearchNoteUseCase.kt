package com.example.leonidsnotesapplication.domain.usecase

import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.domain.repository.NoteRepository

class SearchNoteUseCase(
    private val repository: NoteRepository
) {
    fun execute(query :String?) : ArrayList<Note> {
        return repository.searchNotes(query)
    }
}