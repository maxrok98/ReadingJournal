package com.example.readingjournal

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.readingjournal.databinding.FragmentListOfBooksBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListOfBooks.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListOfBooks : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val mobileArray = arrayOf(
        "Android", "IPhone", "WindowsMobile", "Blackberry",
        "WebOS", "Ubuntu", "Windows7", "Max OS X"
    )

    val notation1: Notation = Notation("First notation on Feynman`s lectures", "Today i have read first chapter of this book")
    val book1: Book = Book("Feynman","Lectures on physics")
    val notation2: Notation = Notation("Nonfiction book", "There are some interesting philosophical conceptions")
    val book2: Book = Book("Taleb", "Antifragile")

    val books = mutableListOf<Book>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        book1.notations.add(notation1)
        book2.notations.add(notation2)
        books.add(book1)
        books.add(book2)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentListOfBooksBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list_of_books, container, false)

        val titles: MutableList<String> = mutableListOf()
        for (titl in books)
        {
            titles.add(titl.Title)
        }
        binding.bookList.adapter =
            context?.let { ArrayAdapter<String>(it, R.layout.support_simple_spinner_dropdown_item, titles) }

        binding.bookList.setOnItemClickListener { parent, v, position, _ ->
            //val selectedItem = parent.getItemAtPosition(position) as String
            v.findNavController().navigate(ListOfBooksDirections.actionListOfBooksToListOfNotations(books[position]))
            //textView.text = "The best football player is $selectedItem"
        }

        binding.newBookButton.setOnClickListener { v: View ->
            v.findNavController().navigate(ListOfBooksDirections.actionListOfBooksToNewBook())
            //Navigation.createNavigateOnClickListener(ListOfBooksDirections.actionListOfBooksToNewBook())
        }

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
        // TODO: Rename and change types and number of parameters
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