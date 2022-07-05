package com.example.leonidsnotesapplication.presentation.notes_feature.viewmodels

import androidx.lifecycle.*
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.domain.repository.FoldersRepository
import com.example.leonidsnotesapplication.domain.repository.NoteRepository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel  @Inject constructor (
    private val noteRepository: NoteRepository,
    private val foldersRepository : FoldersRepository
) : ViewModel() {

    private var _notesLiveData : LiveData<List<Note>> = noteRepository.getAllNotes().asLiveData()
    val notesLiveData get() =  _notesLiveData



    fun searchNotes(query : String?, folder: Folder){
        viewModelScope.launch(Dispatchers.IO) {
           _notesLiveData = noteRepository.searchNotesByFolder(query,folder).asLiveData()
        }
    }

    fun updateNotesByFolder(folder : Folder){
        _notesLiveData = getNotesByFolder(folder)
    }

    fun addNote(note : Note, isNew : Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insertNote(note, isNew)
        }
    }

    fun deleteNote(note : Note){

        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteNote(note)
        }
    }

    fun updateFolderTitle(title : String, folder : Folder){
        viewModelScope.launch(Dispatchers.IO){
            foldersRepository.updateFolderTitle(folder, title)
        }
    }


    private fun getNotesByFolder(folder : Folder) : LiveData<List<Note>> {
        return noteRepository.getNotesByFolder(folder).asLiveData()
    }


}