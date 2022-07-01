package com.example.leonidsnotesapplication.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.domain.model.relations.FolderWithNotes

@Dao
interface NoteDao {

    @Delete
    fun deleteFolder(folder: Folder)

    @Query("UPDATE Note SET isStarred = :state WHERE id = :id")
    fun updateNoteChecked(id : Int, state : Boolean)

    @Query("DELETE FROM Note WHERE id = :id")
    fun deleteNoteById(id : Int)

    @Query("SELECT * FROM Note WHERE id = :id")
    fun getNoteById(id : Int) : Note

    @Query("SELECT title FROM Folder WHERE id = :id")
    fun getFolderTitleById(id : Int) : String

    @Query("SELECT * FROM folder WHERE id = :id")
    fun getFolderById(id : Int) : Folder

    @Query("UPDATE Folder SET title = :title WHERE id = :id")
    fun updateFolderTitle(id : Int, title : String)

    @Query("SELECT * FROM Folder WHERE id = :id")
    fun getFolderWithNotesByFolderId(id : Int) : FolderWithNotes

    @Query("SELECT * FROM Note WHERE folderId = :id ORDER BY isStarred")
    fun getNotesByFolderId(id : Int) : LiveData<List<Note>>

    @Query("SELECT * FROM note WHERE content LIKE :query ORDER BY isStarred")
    fun searchAllNotes(query : String?) : LiveData<List<Note>>

    @Query("SELECT * FROM note WHERE folderId = :id AND content LIKE :query ORDER BY isStarred" )
    fun searchNotesByFolderId(query : String?, id : Int) : LiveData<List<Note>>

    @Query("SELECT * FROM Note ORDER BY isStarred")
    fun getAllNotes() : LiveData<List<Note>>

    @Query("SELECT * FROM  Folder WHERE id = :id")
    fun getFolderWithNotes(id : Int) : List<FolderWithNotes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFolder(folder : Folder)

    @Query("SELECT * FROM Folder")
    fun getAllFolders() : LiveData<List<Folder>>

    @Query("UPDATE folder SET noteCount = noteCount + 1 WHERE id = :id")
    fun updateOnInsertNote(id : Int)

    @Query("UPDATE folder SET noteCount = noteCount - 1 WHERE id = :id")
    fun updateOnDeleteNote(id : Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note : Note)

    @Delete
    suspend fun deleteNote(note : Note)
}