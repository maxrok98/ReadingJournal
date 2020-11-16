package com.example.readingjournal.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.readingjournal.R
import com.example.readingjournal.models.Book

class BooksListViewAdapter(private val fragment: Fragment, booksList: ArrayList<Book>) : BaseAdapter() {

    private var booksList = arrayListOf<Book>()

    init {
        this.booksList = booksList // as MutableList<Book>
    }

    override fun getCount(): Int {
        return booksList.size
    }

    override fun getItem(i: Int): Any {
        return booksList[i]
    }

    override fun getItemId(i: Int): Long {
        return booksList[i].Id
    }

    @SuppressLint("InflateParams", "ViewHolder", "ServiceCast")
    override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup): View {
        var vi: View? = convertView
        val inflater = fragment.activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if(vi == null) {
            vi = inflater.inflate(R.layout.book_list_row, null)
        }
        val title = vi!!.findViewById<TextView>(R.id.title)
        //val genre = vi.findViewById(R.id.genre)
        //val year = vi.findViewById(R.id.year)
        title.text = booksList[i].Title
        //genre.text = moviesList[i].genre
        //year.text = moviesList[i].year
        return vi
    }
}