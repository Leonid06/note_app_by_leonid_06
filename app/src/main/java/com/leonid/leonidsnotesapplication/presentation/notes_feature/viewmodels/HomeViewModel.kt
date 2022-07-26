package com.leonid.leonidsnotesapplication.presentation.notes_feature.viewmodels

import androidx.lifecycle.*
import com.leonid.leonidsnotesapplication.domain.model.Folder
import com.leonid.leonidsnotesapplication.domain.model.Note
import com.leonid.leonidsnotesapplication.domain.repository.FoldersRepository
import com.leonid.leonidsnotesapplication.domain.repository.NoteRepository
import com.leonid.leonidsnotesapplication.presentation.notes_feature.util.SortOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val noteRepository : NoteRepository,
    private val foldersRepository: FoldersRepository
) : ViewModel() {


    private val _notes  = MutableLiveData<ArrayList<Note>>()
    val notes : LiveData<ArrayList<Note>> = _notes

    private val _folders  = MutableLiveData<ArrayList<Folder>>()
    val folders : LiveData<ArrayList<Folder>> = _folders


    private var notesJob : Job = Job()
    private var foldersJob : Job = Job()

    init {
        getFolders()
    }


    fun searchNotes(query : String, option: SortOption){
        notesJob.cancel()
        notesJob = viewModelScope.launch(Dispatchers.IO){
            noteRepository.searchAllNotes(query, option).cancellable().collect{
                _notes.postValue(it as ArrayList<Note>)
            }
        }
    }

    private fun getFolders(){
        foldersJob.cancel()
        foldersJob = viewModelScope.launch(Dispatchers.IO) {
            foldersRepository.getAllFolders(SortOption.ByDate).cancellable().collect{
                _folders.postValue(it as ArrayList<Folder>)
            }
        }
    }


    fun sortNotes(option: SortOption){
        notesJob.cancel()
        notesJob = viewModelScope.launch(Dispatchers.IO) {
            noteRepository.getAllNotes(option).cancellable().collect{
                _notes.postValue(it as ArrayList<Note>)
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