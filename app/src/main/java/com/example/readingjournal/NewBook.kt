package com.example.readingjournal

import android.os.Bundle
import android.view.*
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.readingjournal.database.BooksDatabase
import com.example.readingjournal.databinding.FragmentNewBookBinding
import com.example.readingjournal.viewmodels.NewBookViewModel
import com.example.readingjournal.viewmodelfactories.NewBookViewModelFactory

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewBook.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewBook : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentNewBookBinding
    private lateinit var viewModel: NewBookViewModel
    private lateinit var viewModelFactory: NewBookViewModelFactory

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
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_new_book, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = BooksDatabase.getInstance(application).booksDatabaseDao
        viewModelFactory =
            NewBookViewModelFactory(
                dataSource
            )
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(NewBookViewModel::class.java)

        binding.viewModel = viewModel

        binding.button.setOnClickListener { v ->
            viewModel.addBook(binding.bookAuthor.text.toString(), binding.bookTitle.text.toString())
            v.findNavController().navigate(NewBookDirections.actionNewBookToListOfBooks())
        }
        binding.buttonLoadInfo.setOnClickListener { v ->
            viewModel.getBookFromApi(binding.ISBN.text.toString())
        }
        viewModel.status.observe(viewLifecycleOwner, Observer {
            if(it == "Done") {
                changeImage()
                binding.buttonLoadInfo.text = "Done"
                binding.buttonLoadInfo.contentDescription=getString(R.string.loaded)
            }
        })

        binding.setLifecycleOwner(this)
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_new_book, container, false)
        return binding.root
    }
    fun changeImage(){
        viewModel.property.value?.items!![0]?.info.imgSrcUrl.thumbnail?.let{
            val imgUri = it.toUri().buildUpon().scheme("http").build()
            Glide.with(binding.imageView.context)
                .load(imgUri)
                .apply(RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image))
                .into(binding.imageView)

        }
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
         * @return A new instance of fragment NewBook.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewBook().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}