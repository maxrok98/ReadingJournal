package com.example.readingjournal.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.readingjournal.R
import com.example.readingjournal.databinding.BookListRow2Binding
import com.example.readingjournal.models.Book

class BooksRecyclerViewAdapter(val clickListener: BookListListener, val clickDeleteListener: BookDeleteListener): ListAdapter<Book, BooksRecyclerViewAdapter.ViewHolder>(BookDiffCallBack()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(clickListener, clickDeleteListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor (val binding: BookListRow2Binding) : RecyclerView.ViewHolder(binding.root){

        fun bind(clickListener: BookListListener, clickDeleteListener: BookDeleteListener, item: Book){
            binding.clickListener = clickListener
            binding.clickDeleteListener = clickDeleteListener
            binding.book = item
            item.thumbnailURL?.let {
                val imgUri = it.toUri().buildUpon().scheme("http").build()
                Glide.with(binding.imageViewBook.context)
                    .load(imgUri)
                    .apply(
                        RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image))
                    .into(binding.imageViewBook)
            }
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

class BookDeleteListener( val clickDeleteListener: (bookId: Long) -> Unit){
    fun onDeleteClick(book: Book) = clickDeleteListener(book.Id)
}