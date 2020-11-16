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
import com.example.readingjournal.models.Notation

class NotationsListViewAdapter(private val fragment: Fragment, notationsList: ArrayList<Notation>) : BaseAdapter() {
    private var notationList = arrayListOf<Notation>()

    init {
        this.notationList = notationsList // as MutableList<Book>
    }

    override fun getCount(): Int {
        return notationList.size
    }

    override fun getItem(i: Int): Any {
        return notationList[i]
    }

    override fun getItemId(i: Int): Long {
        return notationList[i].Id
    }

    @SuppressLint("InflateParams", "ViewHolder", "ServiceCast")
    override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup): View {
        var vi: View? = convertView
        val inflater = fragment.activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if(vi == null) {
            vi = inflater.inflate(R.layout.book_list_row, null)
        }
        val title = vi!!.findViewById<TextView>(R.id.title)
        title.text = notationList[i].title
        return vi
    }
}