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
import com.example.readingjournal.databinding.BookListRow2Binding
import com.example.readingjournal.models.Book

class BooksRecyclerViewAdapter(val clickListener: BookListListener): ListAdapter<Book, BooksRecyclerViewAdapter.ViewHolder>(BookDiffCallBack()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(clickListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor (val binding: BookListRow2Binding) : RecyclerView.ViewHolder(binding.root){

        fun bind(clickListener: BookListListener, item: Book){
            binding.clickListener = clickListener
            binding.book = item
        }
        companion object{
            fun from(parent: ViewGroup):ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = BookListRow2Binding.inflate(layoutInflater, parent, false)
                val view = layoutInflater
                    .inflate(R.layout.book_list_row2, parent, false)

                return ViewHolder(binding)
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
class BookListListener(val clickListener: (bookId: Long) -> Unit){
    fun onClick(book: Book) = clickListener(book.Id)
}