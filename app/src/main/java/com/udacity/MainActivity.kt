package com.udacity

import android.app.DownloadManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

/*
Flow

The flow should be that once the user should hit a radio button

There should be a check for whether or not a radio button has been hit - otherwise TOAST.

If the radio button was hit, correspond that button with the download link somehow.

Download should only happen once the loading button is hit.
 */

class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0

    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action

    private var selectedURL: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        val button = findViewById<LoadingButton>(R.id.custom_button)
//        val selectionGroup = findViewById<RadioGroup>(R.id.downloadSelectionGroup)
        button.setOnClickListener {

            // Change this check to - ifRadioButton is not selected, otherwise do else.
            if (downloadSelectionGroup.checkedRadioButtonId == -1) {
                Toast.makeText(this, "Please make a selection, ya dummy", Toast.LENGTH_SHORT).show()
            } else {
//                button.setState(ButtonState.Downloading)
//                download(selectedURL)
//                downloadSelectionGroup.setOnClickListener {
//                    onRadioButtonClicked(this.downloadSelectionGroup)

//                }

                downloadSelectionGroup.setOnClickListener {
                    onRadioButtonClicked(this.downloadSelectionGroup)
                }
                button.setState(ButtonState.Downloading)
            }

        }

//        downloadSelectionGroup.setOnClickListener {
//            onRadioButtonClicked(this.downloadSelectionGroup)
//        }

//        val intent = Intent(this, Playground::class.java)
//        startActivity(intent)
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

//    fun onRadioButtonClicked() {
//        downloadSelectionGroup.setOnCheckedChangeListener { _, checkedId ->
//            selectedURL = when(checkedId){
//                R.id.githubButton -> STARTER_URL
//                R.id.glideButton -> GLIDE_URL
//                R.id.retrofitButton -> RETROFIT_URL
//                else -> {""}
//            }
//        }
//    }


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
    }

    companion object {
        private const val STARTER_URL =
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
        private const val GLIDE_URL =
            "https://github.com/bumptech/glide"
        private const val RETROFIT_URL =
            "https://github.com/square/retrofit"

        private const val CHANNEL_ID = "channelId"

    }

}
