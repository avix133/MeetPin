package com.coding.team.meetpin.activities

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import com.coding.team.meetpin.R
import com.coding.team.meetpin.client_server.netty.ClientHandler
import com.coding.team.meetpin.dao.model.User
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class FriendsListActivity : MenuActivity() {

    lateinit var invitationButton : Button
    lateinit var userList : List<User>
    lateinit var friendsListView : ListView
    private var friendsList : MutableList<String> = arrayListOf()

    @TargetApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_list)
        invitationButton = findViewById(R.id.invitationButton)

        invitationButton.setOnClickListener( {
            val intent = Intent(applicationContext, FriendsInvitationListActivity::class.java)
            intent.putExtra("FROM_ACTIVITY", "FriendsListActivity")
            startActivity(intent)
        })
        userList = listOf<User>()
        println("Sending: ")
        try {
            userList = ClientHandler.getInstance().getFriendList().get(5, TimeUnit.SECONDS).payload as List<User>

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(applicationContext, "Something went wrong... ", Toast.LENGTH_SHORT).show()
        }


        for (u in userList) {
            friendsList.add(u.username)
        }
        friendsListView = findViewById(R.id.friendsList)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, friendsList)
        friendsListView.adapter = adapter

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendNotification(view: View) {

        val channelID = "com.ebookfrenzy.notifydemo.news"

        val notification = Notification.Builder(this@FriendsListActivity, channelID)
                .setContentTitle("Example Notification")
                .setContentText("This is an  example notification.")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setChannelId(channelID)
                .build()
        val notificationManager =
                getSystemService(
                        Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(101, notification)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
