package com.coding.team.meetpin.activities

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import com.coding.team.meetpin.R

class MyProfileActivity : MenuActivity() {


    private lateinit var mapButton: Button
    private lateinit var friendsButton: Button
    private lateinit var joey: WebView
    private lateinit var logOutButton : Button


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val extras = intent.extras
        println("My Profile Activity")

        if ( extras==null) {
            println("In MainActivity: starting AuthenticationActivity")
            val intent = Intent(applicationContext, MyProfileActivity::class.java)
            intent.putExtra("FROM_ACTIVITY", "MainActivity")

            startActivity(intent)
        } else {
            println("In MainActivity: starting MAIN")

            setContentView(R.layout.activity_profile)

            mapButton = findViewById(R.id.mapButton)
            friendsButton = findViewById(R.id.friendsListButton)
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
            logOutButton.setOnClickListener(
                    {
                        val intent = Intent(applicationContext, AuthenticationActivity::class.java)
                        intent.putExtra("FROM_ACTIVITY", "MainActivity")
                        intent.putExtra("LOGGED_IN", true)
                        startActivity(intent)
                    }
            )
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
