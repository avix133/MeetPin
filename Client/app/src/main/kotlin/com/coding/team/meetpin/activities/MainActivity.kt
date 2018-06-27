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
        initClient()
        val serverAuthenticated = ClientHandler.getInstance().authenticate(email)

        if (serverAuthenticated) {
            Toast.makeText(applicationContext, "Logged as: $email", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, MapActivity::class.java)
            intent.putExtra("FROM_ACTIVITY", "MainActivity")
            startActivity(intent)
        }
        else {
            Toast.makeText(applicationContext, "Something went wrong...", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, AuthenticationActivity::class.java)
            startActivity(intent)
        }

    }



    private fun initClient() {
        val client = Client("77.55.222.254", 8081)
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
