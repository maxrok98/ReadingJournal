package com.example.readingjournal.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.readingjournal.models.Notation

class OpenNotationViewModelFactory(private val notation: Notation) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OpenNotationViewModel::class.java)) {
            return OpenNotationViewModel(notation) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
