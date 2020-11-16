package com.example.readingjournal.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.readingjournal.database.BooksDatabaseDao
import com.example.readingjournal.viewmodels.NewBookViewModel

class NewBookViewModelFactory(private val dataSource: BooksDatabaseDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewBookViewModel::class.java)) {
            return NewBookViewModel(
                dataSource
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}