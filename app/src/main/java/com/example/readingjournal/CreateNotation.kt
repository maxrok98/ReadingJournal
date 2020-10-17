package com.example.readingjournal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class CreateNotation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_notation)

        findViewById<Button>(R.id.button4).setOnClickListener{
            showInfo()
        }
    }

    private fun showInfo() {
        val title = findViewById<EditText>(R.id.editTextTextPersonName2)
        val text = findViewById<EditText>(R.id.editTextTextMultiLine)

        val toast = Toast.makeText(applicationContext,"Title: ${title.text}\n Text: ${text.text}\n", Toast.LENGTH_LONG )
        toast.show()

        title.setText("")
        text.setText("")



    }
}