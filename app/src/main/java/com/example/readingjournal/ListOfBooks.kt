package com.example.readingjournal

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.readingjournal.databinding.FragmentListOfBooksBinding
import com.example.readingjournal.viewmodels.ListOfBooksViewModel

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


    private lateinit var binding: FragmentListOfBooksBinding
    private lateinit var viewModel: ListOfBooksViewModel

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

        viewModel = ViewModelProvider(this).get(ListOfBooksViewModel::class.java)

        binding.setLifecycleOwner(this)
        binding.bookList.adapter =
            context?.let { ArrayAdapter<String>(it, R.layout.support_simple_spinner_dropdown_item,
                viewModel.titles.value as MutableList<String>
            ) }

        binding.bookList.setOnItemClickListener{parent, view, position, id ->
            viewModel.books.value?.get(position)?.let {
                ListOfBooksDirections.actionListOfBooksToListOfNotations(
                    it
                )
            }?.let { view.findNavController().navigate(it) }
        }

        binding.newBookButton.setOnClickListener { v: View ->
            v.findNavController().navigate(ListOfBooksDirections.actionListOfBooksToNewBook())
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