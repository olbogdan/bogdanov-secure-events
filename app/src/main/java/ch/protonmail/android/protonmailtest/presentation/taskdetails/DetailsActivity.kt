package ch.protonmail.android.protonmailtest.presentation.taskdetails

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import ch.protonmail.android.protonmailtest.R

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        findViewById<Button>(R.id.task_details_btn_download).setOnClickListener {
            Log.d("DetailActivity", "Downloading the image...")
        }
    }
}
