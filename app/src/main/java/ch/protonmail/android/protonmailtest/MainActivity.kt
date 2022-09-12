package ch.protonmail.android.protonmailtest

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ch.protonmail.android.protonmailtest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.app_name)
        initTabs()
        viewModel.fetchTasks(this)
    }

    private fun initTabs() {
        val adapter = TabsAdapter(this, supportFragmentManager)
        binding.pager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.pager)
    }
}