package com.example.readingjournal.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.readingjournal.database.BooksDatabaseDao
import com.example.readingjournal.database.NotationsDatabaseDao
import com.example.readingjournal.viewmodels.ListOfNotationViewModel
import java.security.CodeSource

class ListOfNotationViewModelFactory(private val bookId: Long,
                                     private val dataSource: NotationsDatabaseDao,
                                     private val bookSource: BooksDatabaseDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListOfNotationViewModel::class.java)) {
            return ListOfNotationViewModel(
                bookId,
                dataSource,
                bookSource
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
