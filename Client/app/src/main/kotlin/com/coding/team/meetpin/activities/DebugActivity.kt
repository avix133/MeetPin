package com.coding.team.meetpin.activities

import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.coding.team.meetpin.client_server.ClientHandler
import com.coding.team.meetpin.R
import com.coding.team.meetpin.client_server.communication.requests.PinDataRequest
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

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
                    println("Sending: " + sendEditText.text.toString())
//                    ClientHandler.getInstance().sendMessage(PinDataRequest(1, 2))
                    val x = ClientHandler.getInstance().getPinData(sendEditText.text.toString().toInt())
                    try {
                        val response = x.get(5, TimeUnit.SECONDS)
                        sendTextView.text = response.payload as String
                    } catch (e : TimeoutException) {
                        println("Timeout!")
                    }
//                    val response = client.sendRequest(sendEditText.text.toString())


                    println("Sent!")

                })

    }

}
