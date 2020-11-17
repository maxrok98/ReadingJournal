package com.example.readingjournal.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.readingjournal.R
import com.example.readingjournal.models.Book

class BooksRecyclerViewAdapter: RecyclerView.Adapter<BooksRecyclerViewAdapter.ViewHolder>() {
    var data = listOf<Book>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        val res = holder.itemView.context.resources

        holder.bookAuthor.text = item.Author
        holder.bookTitle.text = item.Title

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.book_list_row2, parent, false)

        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val bookTitle: TextView = itemView.findViewById(R.id.bookItemTitle)
        val bookAuthor: TextView = itemView.findViewById(R.id.bookItemAuthor)
        //val sleepLength: TextView = itemView.findViewById(R.id.sleep_length)
        //val quality: TextView = itemView.findViewById(R.id.quality_string)
        //val qualityImage: ImageView = itemView.findViewById(R.id.quality_image)
    }
}