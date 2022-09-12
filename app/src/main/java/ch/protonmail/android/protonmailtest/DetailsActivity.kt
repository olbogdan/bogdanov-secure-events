package ch.protonmail.android.protonmailtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        findViewById<Button>(R.id.download).setOnClickListener {
            Log.d("DetailActivity", "Downloading the image...")
        }
    }
}
