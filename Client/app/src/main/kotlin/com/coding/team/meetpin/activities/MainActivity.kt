package com.coding.team.meetpin.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.webkit.WebView
import android.widget.Button
import com.coding.team.meetpin.R
import com.coding.team.meetpin.client_server.netty.Client
import com.coding.team.meetpin.client_server.netty.ClientHandler

class MainActivity : MenuActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val extras = intent.extras
        println("In MainActivity")

        if ( extras==null) {
            println("In MainActivity: starting AuthenticationActivity")
            val intent = Intent(applicationContext, MapActivity::class.java)
            intent.putExtra("FROM_ACTIVITY", "MapActivity")

            startActivity(intent)
        }
    }

    private fun initClient(): ClientHandler {
        val client = Client("192.168.1.139", 8080)
        val clientHandler = ClientHandler.getInstance()

        println("Initializing client...")
        Thread({
            client.start(clientHandler)
        }).start()
        println("Returning clientHandler")


        return clientHandler
    }
}
