package com.example.leonidsnotesapplication.presentation.notes_feature.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleNoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel(){

    fun addNote(note : Note, isNew : Boolean){
        viewModelScope.launch(Dispatchers.IO){
            noteRepository.insertNote(note, isNew)
        }
    }
}