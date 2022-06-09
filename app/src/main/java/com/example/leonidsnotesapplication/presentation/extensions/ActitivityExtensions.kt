package com.example.leonidsnotesapplication.presentation.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService

fun Activity.showKeyboard(view : View){
    (this.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.apply {
            currentFocus?.clearFocus()
            view.requestFocus()
            showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
}