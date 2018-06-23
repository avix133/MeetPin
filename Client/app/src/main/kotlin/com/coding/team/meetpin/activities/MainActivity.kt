package com.coding.team.meetpin.activities

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.Toast

import com.coding.team.meetpin.client_server.netty.Client
import com.coding.team.meetpin.client_server.netty.ClientHandler

class MainActivity : MenuActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        println("In MainActivity")

        val email = intent.getStringExtra("AUTHENTICATION_EMAIL")

        Toast.makeText(applicationContext, "Logged as: $email", Toast.LENGTH_SHORT).show()

        val intent = Intent(applicationContext, MapActivity::class.java)
        intent.putExtra("FROM_ACTIVITY", "MainActivity")
        startActivity(intent)

        initClient()
    }



    private fun initClient() {
        val client = Client("192.168.1.14", 8081)
        val clientHandler = ClientHandler.getInstance()

        println("Initializing client...")
        Thread({
            try {
                client.start(clientHandler)
            } catch (e : Exception) {
                println("Couldn't connect to server")
            }
        }).start()
    }
}
