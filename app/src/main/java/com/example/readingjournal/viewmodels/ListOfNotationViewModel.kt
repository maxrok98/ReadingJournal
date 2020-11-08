package com.example.readingjournal.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.readingjournal.models.Book
import com.example.readingjournal.models.Notation

class ListOfNotationViewModel(book: Book) : ViewModel(){

    private val _book = MutableLiveData<Book>()
    val book: LiveData<Book>
        get() = _book

    private val _titles = MutableLiveData<MutableList<String>>()
    val titles: LiveData<MutableList<String>>
        get(){
            var list = mutableListOf<String>()
            for (b in this._book.value?.notations!!) list.add(b.title)
            _titles.value = list
            return _titles
        }

    init{
        _book.value = book
    }

    fun addNotation(title: String, text: String){
        _book.value?.notations?.add(Notation(title, text))
    }

}