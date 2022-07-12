package com.leonid.leonidsnotesapplication.presentation.notes_feature.util

sealed class SortOption {
    object ByTitle : SortOption()
    object ByDate : SortOption()
}
