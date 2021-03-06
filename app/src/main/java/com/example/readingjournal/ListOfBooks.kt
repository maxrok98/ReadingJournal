package com.example.readingjournal

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.readingjournal.adapters.BookDeleteListener
import com.example.readingjournal.adapters.BookListListener
import com.example.readingjournal.adapters.BooksListViewAdapter
import com.example.readingjournal.adapters.BooksRecyclerViewAdapter
import com.example.readingjournal.database.BooksDatabase
import com.example.readingjournal.databinding.FragmentListOfBooksBinding
import com.example.readingjournal.models.Book
import com.example.readingjournal.viewmodels.ListOfBooksViewModel
import com.example.readingjournal.viewmodelfactories.ListOfBooksViewModelFactory
import com.google.android.material.chip.Chip

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListOfBooks.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListOfBooks : Fragment() {
    private var param1: String? = null
    private var param2: String? = null


    private lateinit var binding: FragmentListOfBooksBinding
    private lateinit var viewModel: ListOfBooksViewModel
    private lateinit var viewModelFactory: ListOfBooksViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list_of_books, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = BooksDatabase.getInstance(application).booksDatabaseDao
        viewModelFactory =
            ListOfBooksViewModelFactory(
                dataSource
            )
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ListOfBooksViewModel::class.java)


        //viewModel.books.observe(viewLifecycleOwner, Observer<List<Book>>() {
        //    binding.bookList.adapter =
        //        BooksListViewAdapter(
        //            this,
        //            it as ArrayList<Book>
        //        )
        //})

        val adapter = BooksRecyclerViewAdapter(BookListListener { bookId ->
            view?.findNavController()?.navigate(ListOfBooksDirections.actionListOfBooksToListOfNotations(bookId))
        },
        BookDeleteListener { bookId -> viewModel.deleteBook(bookId)})

        binding.bookList.adapter = adapter

        viewModel.books.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.authorList.observe(viewLifecycleOwner, object: Observer<List<String>> {
            override fun onChanged(data: List<String>?) {
                data ?: return
                val chipGroup = binding.authorList
                val inflator = LayoutInflater.from(chipGroup.context)

                val children = data.map { regionName ->
                    val chip = inflator.inflate(R.layout.author, chipGroup, false) as Chip
                    chip.text = regionName
                    chip.tag = regionName
                    chip.setOnCheckedChangeListener { button, isChecked ->
                        viewModel.onFilterChanged(button.tag as String, isChecked)
                    }
                    chip
                }

                chipGroup.removeAllViews()

                for (chip in children) {
                    chipGroup.addView(chip)
                }
            }
        })

        //binding.bookList.setOnItemClickListener( AdapterView.OnItemClickListener { parent, view, position, id ->
        //    val it = parent.getItemIdAtPosition(position)
        //})

        binding.newBookButton.setOnClickListener { v: View ->
            v.findNavController().navigate(ListOfBooksDirections.actionListOfBooksToNewBook())
        }

        binding.setLifecycleOwner(this)
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!, requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListOfBooks.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListOfBooks().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}