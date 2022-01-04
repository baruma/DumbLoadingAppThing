package com.udacity

import android.app.DownloadManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0

    private lateinit var notificationManager: NotificationManager
    private lateinit var action: NotificationCompat.Action
    private lateinit var loadingButton: LoadingButton
    private var selectedURL: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        loadingButton = findViewById<LoadingButton>(R.id.custom_button)
        loadingButton.setOnClickListener {

            // Change this check to - ifRadioButton is not selected, otherwise do else.
            if (downloadSelectionGroup.checkedRadioButtonId == -1) {
                Toast.makeText(this, "Please make a selection, ya dummy", Toast.LENGTH_SHORT).show()
            } else {
                loadingButton.setState(ButtonState.Downloading)
                Log.d("Lookatme", "onCreate: $selectedURL")
                download(selectedURL)

            }
        }
        loadingButton.setState(ButtonState.Ready)
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.githubButton ->
                    if (checked) {
                        selectedURL = STARTER_URL
                    }
                R.id.glideButton ->
                    if (checked) {
                        selectedURL = GLIDE_URL
                    }
                R.id.retrofitButton ->
                    if (checked) {
                        selectedURL = RETROFIT_URL
                    }
            }
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
        }
    }

    // set up a listener to observe for error case to set state back to Ready state.
    private fun download(url: String) {
        val request =
            DownloadManager.Request(Uri.parse(url))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID =
            downloadManager.enqueue(request)// enqueue puts the download request in the queue.

        val onComplete: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(ctxt: Context, intent: Intent) {
                // your code
                val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (id == downloadID) {
                    //create notification
                    LoadingApp.instance?.notificationManager?.createNotification()
                    loadingButton.setState(ButtonState.Ready)
                }
            }
        }
        registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    companion object {
        private const val STARTER_URL =
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
        private const val GLIDE_URL =
            "https://github.com/bumptech/glide"
        private const val RETROFIT_URL =
            "https://github.com/square/retrofit"
    }

}