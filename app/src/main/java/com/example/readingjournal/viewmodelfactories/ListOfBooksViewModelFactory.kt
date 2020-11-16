package com.example.readingjournal.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.readingjournal.database.BooksDatabaseDao
import com.example.readingjournal.viewmodels.ListOfBooksViewModel

class ListOfBooksViewModelFactory(private val dataSource: BooksDatabaseDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListOfBooksViewModel::class.java)) {
            return ListOfBooksViewModel(
                dataSource
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}