package ch.protonmail.android.protonmailtest.presentation.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import ch.protonmail.android.protonmailtest.R
import ch.protonmail.android.protonmailtest.databinding.ActivityMainBinding
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