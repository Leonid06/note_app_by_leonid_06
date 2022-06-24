package com.example.leonidsnotesapplication.domain.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.domain.model.Note

data class FolderWithNotes(
    @Embedded val folder : Folder,
    @Relation(
        parentColumn = "id",
        entityColumn = "folderId"
    )
    val notes : List<Note>
)