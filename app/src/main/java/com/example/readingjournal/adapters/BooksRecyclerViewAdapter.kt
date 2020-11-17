package com.example.readingjournal.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.readingjournal.R
import com.example.readingjournal.models.Book

class BooksRecyclerViewAdapter: ListAdapter<Book, BooksRecyclerViewAdapter.ViewHolder>(BookDiffCallBack()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val bookTitle: TextView = itemView.findViewById(R.id.bookItemTitle)
        val bookAuthor: TextView = itemView.findViewById(R.id.bookItemAuthor)

        fun bind(item: Book){
            bookAuthor.text = item.Author
            bookTitle.text = item.Title
        }
        companion object{
            fun from(parent: ViewGroup):ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.book_list_row2, parent, false)

                return ViewHolder(view)
            }
        }
    }
}

class BookDiffCallBack: DiffUtil.ItemCallback<Book>(){
    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem.Id == newItem.Id
    }

    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem == newItem
    }

}