package com.example.readingjournal

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.*
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.readingjournal.databinding.FragmentListOfNotationsBinding
import com.example.readingjournal.databinding.FragmentOpenNotationBinding
import com.example.readingjournal.viewmodels.ListOfNotationViewModel
import com.example.readingjournal.viewmodels.ListOfNotationViewModelFactory
import com.example.readingjournal.viewmodels.OpenNotationViewModel
import com.example.readingjournal.viewmodels.OpenNotationViewModelFactory
import timber.log.Timber
import androidx.core.content.getSystemService

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val LIKES_COUNT = "likesCount"

/**
 * A simple [Fragment] subclass.
 * Use the [OpenNotation.newInstance] factory method to
 * create an instance of this fragment.
 */
class OpenNotation : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var likesCount = 0

    private lateinit var binding: FragmentOpenNotationBinding
    private lateinit var viewModel: OpenNotationViewModel
    private lateinit var viewModelFactory: OpenNotationViewModelFactory

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
        var args = arguments?.let { OpenNotationArgs.fromBundle(it) }

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_open_notation, container, false)

        if(args != null) {
            viewModelFactory = OpenNotationViewModelFactory(args.notation)
        }
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(OpenNotationViewModel::class.java)
        binding.viewModel = viewModel

        binding.setLifecycleOwner(this)

        viewModel.eventBuzz.observe(viewLifecycleOwner, Observer { buzzType ->
            if (buzzType != OpenNotationViewModel.BuzzType.NO_BUZZ) {
                buzz(buzzType.pattern)
                viewModel.onBuzzComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }
    private fun buzz(pattern: LongArray) {
        val buzzer = activity?.getSystemService<Vibrator>()
        buzzer?.let {
            // Vibrate for 500 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                //deprecated in API 26
                buzzer.vibrate(pattern, -1)
            }
        }
    }

    /**
     * Called when the user navigates away from the app but might come back
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(LIKES_COUNT, likesCount)
        Timber.i("onSaveInstanceState Called")
        super.onSaveInstanceState(outState)
    }

    //override fun onRestoreInstanceState(savedInstanceState: Bundle) {
    //    super.onRestoreInstanceState(savedInstanceState)
    //    Timber.i("onRestoreInstanceState Called")
    //}

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.notation_menu, menu)
        // check if the activity resolves
        if (null == getShareIntent().resolveActivity(requireActivity().packageManager)) {
            // hide the menu item if it doesn't resolve
            menu.findItem(R.id.share)?.isVisible = false
        }
    }

    private fun getShareIntent() : Intent {
        val args = OpenNotationArgs.fromBundle(requireArguments())
        return ShareCompat.IntentBuilder.from(requireActivity())
            .setText("My new notation\n Title: ${args.notation.title}\n Text: ${args.notation.text}")
            .setType("text/plain")
            .intent
    }

    private fun shareSuccess() {
        startActivity(getShareIntent())
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OpenNotation.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OpenNotation().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}