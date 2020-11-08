package com.example.readingjournal.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.readingjournal.models.Book

class ListOfNotationViewModelFactory(private val book: Book) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListOfNotationViewModel::class.java)) {
            return ListOfNotationViewModel(book) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
