package com.example.readingjournal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.readingjournal.databinding.FragmentListOfBooksBinding
import com.example.readingjournal.databinding.FragmentListOfNotationsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListOfNotations.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListOfNotations : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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

        val binding: FragmentListOfNotationsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list_of_notations, container, false)
        if (args != null) {
            binding.book = args.book
        }

        val titles: MutableList<String> = mutableListOf()
        if (args != null) {
            for (titl in args.book.notations)
            {
                titles.add(titl.title)
            }
        }
        binding.listOfNotations.adapter  =
            context?.let { ArrayAdapter<String>(it, R.layout.support_simple_spinner_dropdown_item, titles) }

        binding.listOfNotations.setOnItemClickListener { parent, v, position, _ ->
            //val selectedItem = parent.getItemAtPosition(position) as String
            if (args != null) {
                v.findNavController().navigate(ListOfNotationsDirections.actionListOfNotationsToOpenNotation(args.book.notations[position]))
            }
            //textView.text = "The best football player is $selectedItem"
        }

        return binding.root
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
        // TODO: Rename and change types and number of parameters
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