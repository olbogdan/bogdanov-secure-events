package ch.protonmail.android.protonmailtest

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        findViewById<Button>(R.id.download).setOnClickListener {
            Log.d("DetailActivity", "Downloading the image...")
        }
    }
}
