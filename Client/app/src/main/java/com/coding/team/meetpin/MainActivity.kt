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
        settingsButton = findViewById(R.id.settingsButton)
        joey = findViewById(R.id.joey)
        joey.loadUrl("file:///android_asset/Joey.html")


        mapButton.setOnClickListener(
        {
            val intent : Intent = Intent(applicationContext, MapActivity::class.java)
            startActivity(intent)
        })
        friendsButton.setOnClickListener(
        {
            val intent : Intent = Intent(applicationContext, FriendsListActivity::class.java)
            startActivity(intent)
        })
        settingsButton.setOnClickListener(
        {
            val intent : Intent = Intent(applicationContext, MapActivity::class.java)
            startActivity(intent)
        })


    }








}
