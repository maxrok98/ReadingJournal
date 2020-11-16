package com.example.readingjournal.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.readingjournal.database.NotationsDatabaseDao
import com.example.readingjournal.viewmodels.OpenNotationViewModel

class OpenNotationViewModelFactory(private val notationId: Long,
                                   private val dataSource: NotationsDatabaseDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OpenNotationViewModel::class.java)) {
            return OpenNotationViewModel(
                notationId,
                dataSource
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
