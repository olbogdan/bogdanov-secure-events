package ch.protonmail.android.protonmailtest.presentation.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ch.protonmail.android.protonmailtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
    }
}