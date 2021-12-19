package com.udacity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Playground : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playground)

        val button = findViewById<PlaygroundButton>(R.id.playgroundButton)
        button.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                button.startLoadAnimation()
            }
        })
    }
}