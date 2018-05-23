package com.coding.team.meetpin.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import android.widget.Button
import com.coding.team.meetpin.client_server.netty.Client
import com.coding.team.meetpin.client_server.netty.ClientHandler
import com.coding.team.meetpin.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var mapButton: Button
    private lateinit var friendsButton: Button
    private lateinit var debugButton: Button
    private lateinit var joey: WebView
    private lateinit var logOutButton : Button
    private lateinit var clientHandler : ClientHandler



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val extras = intent.extras
        println("In MainActivity")

        if ( extras==null) {
            println("In MainActivity: starting AuthenticationActivity")
            val intent = Intent(applicationContext, AuthenticationActivity::class.java)
            intent.putExtra("FROM_ACTIVITY", "MainActivity")

            startActivity(intent)

        } else {
            println("In MainActivity: starting MAIN")

            setContentView(R.layout.activity_main)
            clientHandler = initClient()


            mapButton = findViewById(R.id.mapButton)
            friendsButton = findViewById(R.id.friendsListButton)
            debugButton = findViewById(R.id.mainDebugButton)
            logOutButton = findViewById(R.id.logOutButton)
            joey = findViewById(R.id.joey)
            joey.loadUrl("file:///android_asset/Joey.html")




            mapButton.setOnClickListener(
                    {
                        val intent = Intent(applicationContext, MapActivity::class.java)
                        intent.putExtra("FROM_ACTIVITY", "MainActivity")
                        startActivity(intent)
                    })
            friendsButton.setOnClickListener(
                    {
                        println("Friends")
                        val intent = Intent(applicationContext, FriendsListActivity::class.java)
                        intent.putExtra("FROM_ACTIVITY", "MainActivity")
                        startActivity(intent)
                    })
            mainDebugButton.setOnClickListener(
                    {
                        val intent = Intent(applicationContext, DebugActivity::class.java)
                        intent.putExtra("FROM_ACTIVITY", "MainActivity")
                        startActivity(intent)
                    }
            )
            logOutButton.setOnClickListener(
                    {
                        val intent = Intent(applicationContext, AuthenticationActivity::class.java)
                        intent.putExtra("FROM_ACTIVITY", "MainActivity")
                        intent.putExtra("LOGGED_IN", true)
                        startActivity(intent)
                    }
            )
        }
    }

    fun initClient(): ClientHandler {
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
