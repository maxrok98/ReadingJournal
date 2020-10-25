package com.example.readingjournal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.readingjournal.databinding.ActivityMainBinding
import timber.log.Timber
import java.util.*
import com.example.readingjournal.Timer

class MainActivity : AppCompatActivity(), LifecycleObserver {
    var num: Int = 0;
    private lateinit var Timer: Timer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("onCreate Called")
        Timer = Timer(this.lifecycle)
        //setContentView(R.layout.activity_main)
        @Suppress("UNUSED_VARIABLE")
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

    }

    /** Lifecycle Methods **/

    override fun onStart() {
        super.onStart()
        Timber.i("onStart Called")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("onResume Called")
    }

    override fun onPause() {
        super.onPause()
        Timber.i("onPause Called")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("onStop Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy Called")
    }

    override fun onRestart() {
        super.onRestart()
        Timber.i("onRestart Called")
    }

}