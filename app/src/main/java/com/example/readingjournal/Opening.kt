package com.example.readingjournal

import android.os.Bundle
import android.view.*
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.readingjournal.databinding.FragmentOpeningBinding
import com.example.readingjournal.viewmodelfactories.OpeningViewModelFactory
import com.example.readingjournal.viewmodels.OpeningViewModel

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Opening.newInstance] factory method to
 * create an instance of this fragment.
 */
class Opening : Fragment() {
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
        val binding: FragmentOpeningBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_opening, container, false)
        binding.openButton.setOnClickListener { v: View ->
            v.findNavController().navigate(OpeningDirections.actionOpeningToListOfBooks())
        }

        val app = requireNotNull(this.activity).application
        val viewModelFactory = OpeningViewModelFactory(app)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(OpeningViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.lastBook.observe(viewLifecycleOwner, Observer{
            binding.viewModel = viewModel
            it?.thumbnailURL?.let {
                val imgUri = it.toUri().buildUpon().scheme("http").build()
                Glide.with(binding.imageView4.context)
                    .load(imgUri)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_animation)
                            .error(R.drawable.ic_broken_image))
                    .into(binding.imageView4)
            }

        })



        setHasOptionsMenu(true)
        //setHasOptionsMenu(true)
        return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_opening, container, false)
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
         * @return A new instance of fragment Opening.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Opening().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}