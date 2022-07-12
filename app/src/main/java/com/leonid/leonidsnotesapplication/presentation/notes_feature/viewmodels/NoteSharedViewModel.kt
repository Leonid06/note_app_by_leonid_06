package com.leonid.leonidsnotesapplication.presentation.notes_feature.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leonid.leonidsnotesapplication.domain.model.Note
import com.leonid.leonidsnotesapplication.presentation.notes_feature.util.SortOption
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteSharedViewModel @Inject constructor(

): ViewModel() {

    private val _sortOption  = MutableLiveData<SortOption>(SortOption.ByDate)
    val sortOption : LiveData<SortOption> = _sortOption

    private val _selectedNote : MutableLiveData<Note> = MutableLiveData<Note>()

    val selectedNote : LiveData<Note> get()= _selectedNote

    private val _deleteNote : MutableLiveData<Note> = MutableLiveData<Note>()

    val deleteNote: LiveData<Note> get()= _deleteNote

    fun selectNote(note : Note){
        _selectedNote.value = note
    }
    fun selectDeleteNote(note: Note){
        _deleteNote.value = note
    }

    fun selectSortOption(option : SortOption){
        _sortOption.value  = option
    }
}