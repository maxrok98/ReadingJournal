package com.example.readingjournal.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.readingjournal.models.Book
import com.example.readingjournal.models.Notation

class ListOfBooksViewModel() : ViewModel(){

    val notation1: Notation =
        Notation(
            "First notation on Feynman`s lectures",
            "Today i have read first chapter of this book"
        )
    val book1: Book =
        Book("Feynman", "Lectures on physics")
    val notation2: Notation =
        Notation(
            "Nonfiction book",
            "There are some interesting philosophical concepts"
        )
    val book2: Book =
        Book("Taleb", "Antifragile")

    //val books = mutableListOf<Book>()
    private val _books = MutableLiveData<MutableList<Book>>()
    val books: LiveData<MutableList<Book>>
        get() = _books

    private val _titles = MutableLiveData<MutableList<String>>()
    val titles: LiveData<MutableList<String>>
        get(){
            var list = mutableListOf<String>()
            for (b in this._books.value!!) list.add(b.Title)
            _titles.value = list
            return _titles
        }
    init{
        book1.notations.add(notation1)
        book2.notations.add(notation2)
        _books.value = mutableListOf(book1, book2)
    }
}