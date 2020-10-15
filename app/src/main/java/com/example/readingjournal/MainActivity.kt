package com.example.readingjournal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    var num: Int = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonNotation = findViewById<Button>(R.id.new_notation_button)
        buttonNotation.setOnClickListener{
            openCreateNotation()
        }
    }

    private fun openCreateNotation() {
        val randomIntent = Intent(this, CreateNotation::class.java)
        startActivity(randomIntent)
    }

    fun showRandom(view: View){
        val randomIntent = Intent(this, CreateBook::class.java)

        randomIntent.putExtra(CreateBook.JUST_KEY, num)

        startActivity(randomIntent)
    }
}