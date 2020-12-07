package com.example.readingjournal

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.readingjournal.adapters.BooksListViewAdapter
import com.example.readingjournal.adapters.NotationsListViewAdapter
import com.example.readingjournal.database.BooksDatabase
import com.example.readingjournal.databinding.FragmentListOfNotationsBinding
import com.example.readingjournal.models.Book
import com.example.readingjournal.models.Notation
import com.example.readingjournal.viewmodels.ListOfNotationViewModel
import com.example.readingjournal.viewmodelfactories.ListOfNotationViewModelFactory
import com.google.android.material.snackbar.Snackbar

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListOfNotations.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListOfNotations : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentListOfNotationsBinding
    private lateinit var viewModel: ListOfNotationViewModel
    private lateinit var viewModelFactory: ListOfNotationViewModelFactory

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
        // Inflate the layout for this fragment
        var args = arguments?.let { ListOfNotationsArgs.fromBundle(it) }

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list_of_notations, container, false)

        val application = requireNotNull(this.activity).application
        val notationsSource = BooksDatabase.getInstance(application).notationsDatabaseDao
        val booksSource = BooksDatabase.getInstance(application).booksDatabaseDao
        if(args != null) {
            viewModelFactory =
                ListOfNotationViewModelFactory(
                    args.bookId,
                    notationsSource,
                    booksSource
                )
        }
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ListOfNotationViewModel::class.java)


        viewModel.notation.observe(viewLifecycleOwner, Observer<List<Notation>>() {
            binding.listOfNotations.adapter =
                NotationsListViewAdapter(
                    this,
                    it as ArrayList<Notation>
                )
        })
        viewModel.showSnackBarEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.cleared_notations_message),
                    Snackbar.LENGTH_SHORT // How long to display the message.
                ).show()
                // Reset state to make sure the snackbar is only shown once, even if the device
                // has a configuration change.
                viewModel.doneShowingSnackbar()
            }
        })
        //binding.listOfNotations.adapter  =
        //    context?.let { ArrayAdapter<String>(it, R.layout.support_simple_spinner_dropdown_item,
        //        viewModel.titles.value as MutableList<String>
        //    ) }

        binding.listOfNotations.setOnItemClickListener { parent, v, position, _ ->
            val id = parent.getItemIdAtPosition(position)
            v.findNavController().navigate(ListOfNotationsDirections.actionListOfNotationsToOpenNotation(id))
        }

        binding.newNotationButton.setOnClickListener { v ->
            viewModel.addNotation(binding.notationTitle.text.toString(), binding.notationText.text.toString())
        }

        binding.viewModel = viewModel
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
         * @return A new instance of fragment ListOfNotations.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListOfNotations().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}