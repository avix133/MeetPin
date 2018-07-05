package com.coding.team.meetpin.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.coding.team.meetpin.R
import com.coding.team.meetpin.client_server.netty.ClientHandler

class InviteFriendsActivity : MenuActivity() {

    lateinit var invite: Button
    lateinit var cancel: Button
    lateinit var sendEmail: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_friends)

        invite = findViewById(R.id.invite)
        cancel = findViewById(R.id.cancel)
        sendEmail = findViewById(R.id.sendEmail)

        invite.setOnClickListener() {
            ClientHandler.getInstance().inviteFriend(sendEmail.text.toString())
            Toast.makeText(applicationContext, "Invitation sent", Toast.LENGTH_SHORT).show()
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
