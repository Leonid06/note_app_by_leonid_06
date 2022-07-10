package com.example.leonidsnotesapplication.presentation.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun Activity.showKeyboard(view : View){
    (this.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.apply {
            currentFocus?.clearFocus()
            view.requestFocus()
            showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
}

fun Activity.hideKeyBoard(view : View){
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken,0)
}


fun Activity.getDate() : String {
    val localDate = LocalDate.now()
    val dateFormatter : DateTimeFormatter = DateTimeFormatter
        .ofLocalizedDate(FormatStyle.MEDIUM)
        .withZone(ZoneId.systemDefault())
    return localDate.format(dateFormatter)
}