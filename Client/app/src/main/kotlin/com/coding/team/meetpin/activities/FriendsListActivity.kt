package com.coding.team.meetpin.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.coding.team.meetpin.R

class FriendsListActivity : AppCompatActivity() {
    lateinit var resultTextView : TextView
    lateinit var returnButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_list)
        resultTextView = findViewById(R.id.resultTextView)
        returnButton = findViewById(R.id.returnButton)

        returnButton.setOnClickListener(
                {
                    val intent : Intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }
        )
    }
}
