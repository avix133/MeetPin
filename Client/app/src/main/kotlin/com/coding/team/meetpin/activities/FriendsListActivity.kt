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
import android.widget.Button
import android.widget.TextView
import com.coding.team.meetpin.R

class FriendsListActivity : MenuActivity() {
    lateinit var resultTextView : TextView
    lateinit var returnButton : Button

    @TargetApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_list)
        resultTextView = findViewById(R.id.resultTextView)
        returnButton = findViewById(R.id.returnButton)

        returnButton.setOnClickListener(
                {
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    intent.putExtra("FROM_ACTIVITY", "FriendsListActivity")
                    startActivity(intent)
                }
        )

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
