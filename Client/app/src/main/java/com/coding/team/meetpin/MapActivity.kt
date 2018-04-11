package com.coding.team.meetpin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MapActivity : AppCompatActivity() {

    lateinit var returnButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        returnButton = findViewById(R.id.returnButton)

        returnButton.setOnClickListener(
                {
                    val intent : Intent = Intent(applicationContext,MainActivity::class.java)
                    startActivity(intent)
                }
        )

    }
}
