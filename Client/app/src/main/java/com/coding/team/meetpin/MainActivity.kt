package com.coding.team.meetpin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button

class MainActivity : AppCompatActivity() {


    lateinit var mapButton : Button
    lateinit var friendsButton : Button
    lateinit var settingsButton : Button
    lateinit var joey : WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapButton = findViewById(R.id.mapButton)
        friendsButton = findViewById(R.id.friendsListButton)
        joey = findViewById(R.id.joey)
        joey.loadUrl("file:///android_asset/Joey.html")


        mapButton.setOnClickListener(
        {
            val intent = Intent(applicationContext, MapActivity::class.java)
            startActivity(intent)
        })
        friendsButton.setOnClickListener(
        {
            val intent = Intent(applicationContext, FriendsListActivity::class.java)
            startActivity(intent)
        })



    }








}
