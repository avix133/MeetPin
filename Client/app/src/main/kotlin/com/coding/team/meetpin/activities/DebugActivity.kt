package com.coding.team.meetpin.activities

import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.coding.team.meetpin.Client
import com.coding.team.meetpin.R

/**
 * Created by dawid on 18.04.18.
 */


class DebugActivity : AppCompatActivity() {

    lateinit var sendButton: Button
    lateinit var sendEditText: EditText
    lateinit var sendTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debug)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)


        sendButton = findViewById(R.id.debugSendButton)
        sendEditText = findViewById(R.id.debugSendEditText)
        sendTextView = findViewById(R.id.debugSendTextView)


        sendButton.setOnClickListener(
                {
                    println("Im here!")
                    val client = Client(8080, "192.168.1.118")
                    println("Sending: " + sendEditText.text.toString())
                    val response = client.sendRequest(sendEditText.text.toString())
                    sendTextView.text = response

                    println("Sent!")

                })

    }

}
