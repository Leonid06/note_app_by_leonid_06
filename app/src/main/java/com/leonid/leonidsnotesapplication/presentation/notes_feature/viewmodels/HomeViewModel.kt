package com.leonid.leonidsnotesapplication.presentation.notes_feature.viewmodels

import androidx.lifecycle.*
import com.leonid.leonidsnotesapplication.domain.model.Note
import com.leonid.leonidsnotesapplication.domain.repository.NoteRepository
import com.leonid.leonidsnotesapplication.presentation.notes_feature.util.SortOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val noteRepository : NoteRepository
) : ViewModel() {


    private val _notesLiveData  = MutableLiveData<ArrayList<Note>>()
    val notesLiveData : LiveData<ArrayList<Note>> = _notesLiveData

    private var currentJob : Job = Job()

    fun searchNotes(query : String, option: SortOption){
        currentJob.cancel()
        currentJob = viewModelScope.launch(Dispatchers.IO){
            noteRepository.searchAllNotes(query, option).cancellable().collect{
                _notesLiveData.postValue(it as ArrayList<Note>)
            }
        }
    }

    fun sortNotes(option: SortOption){
        currentJob.cancel()
        currentJob = viewModelScope.launch(Dispatchers.IO) {
            noteRepository.getAllNotes(option).cancellable().collect{
                _notesLiveData.postValue(it as ArrayList<Note>)
            }
        }
    }

    fun deleteNote(note : Note){
        viewModelScope.launch(Dispatchers.IO){
            noteRepository.deleteNote(note)
        }
    }

    fun updateNoteChecked(note : Note){
        viewModelScope.launch(Dispatchers.IO){
            noteRepository.insertNote(note, false)
        }
    }

}