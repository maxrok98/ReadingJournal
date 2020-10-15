package com.example.readingjournal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CreateBook : AppCompatActivity() {
    companion object{
        val JUST_KEY = "KEY"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_book)
    }
}