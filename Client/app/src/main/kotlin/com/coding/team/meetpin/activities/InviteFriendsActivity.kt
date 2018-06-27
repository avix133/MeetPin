package com.coding.team.meetpin.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.widget.Button
import com.coding.team.meetpin.R

class InviteFriendsActivity : MenuActivity() {

    lateinit var invite: Button
    lateinit var cancel: Button
    lateinit var sendEmail: TextInputEditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_friends)

        invite = findViewById(R.id.invite)
        cancel = findViewById(R.id.cancel)
        sendEmail = findViewById(R.id.emailInput)

        invite.setOnClickListener() {

        }

        cancel.setOnClickListener() {
            val intent = Intent(applicationContext, MapActivity::class.java)
            intent.putExtra("FROM_ACTIVITY", "FriendsListActivity")
            startActivity(intent)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
