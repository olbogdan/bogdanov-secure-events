package com.bogdanov.android.cryptoevent.presentation.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.bogdanov.android.cryptoevent.R
import com.bogdanov.android.cryptoevent.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
    }

    override fun onBackPressed() {
        if (Navigation.findNavController(this, R.id.nav_host_fragment).popBackStack().not()) {
            finish()
        }
    }
}