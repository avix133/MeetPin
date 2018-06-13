package com.coding.team.meetpin.activities

import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.coding.team.meetpin.R
import com.coding.team.meetpin.client_server.netty.ClientHandler
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Created by dawid on 18.04.18.
 */


class DebugActivity : MenuActivity() {

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
                    println("Sending: " + sendEditText.text.toString())
                    val future = ClientHandler.getInstance().getPinData(sendEditText.text.toString().toInt())
                    if (future != null) {
                        try {
                            val response = future.get(5, TimeUnit.SECONDS)
                            sendTextView.text = response.payload as String
                            System.out.println(response.payload as String)
                        } catch (e : TimeoutException) {
                            println("Timeout!")
                        }

                    }
                    else {
                        Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }


                })

    }

}
