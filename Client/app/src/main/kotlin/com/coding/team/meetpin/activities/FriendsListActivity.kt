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

    lateinit var returnButton : Button
    lateinit var invitationButton : Button
    lateinit var userList : List<User>
    lateinit var friendsListView : ListView
    private var friendsList : MutableList<String> = arrayListOf()

    @TargetApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_list)

        returnButton = findViewById(R.id.returnButton)
        invitationButton = findViewById(R.id.invitationButton)
        returnButton.setOnClickListener(
        {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra("FROM_ACTIVITY", "FriendsListActivity")
            startActivity(intent)
        }
        )
        invitationButton.setOnClickListener( {
            val intent = Intent(applicationContext, FriendsInvitationListActivity::class.java)
            intent.putExtra("FROM_ACTIVITY", "FriendsListActivity")
            startActivity(intent)
        })
        userList = listOf<User>()
        println("Sending: ")
        try {
            val future = ClientHandler.getInstance().friendList
            if (future != null) {
                try {
                    val response = future.get(5, TimeUnit.SECONDS)
                    if (response.payload != null) {
                        try {
                            userList = response.payload as List<User>
                            System.out.println(userList.toString())
                        }catch (e:Exception){
                            Toast.makeText(applicationContext, "Something went wrong with casting", Toast.LENGTH_SHORT).show()
                            val intent = Intent(applicationContext, FriendsListActivity::class.java)
                            intent.putExtra("FROM_ACTIVITY", "FriendsListActivity")
                            startActivity(intent)
                        }
                    }
                } catch (e : TimeoutException) {
                    println("Timeout!")
                }
            }
            else {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        } catch (e :Exception) {
            e.printStackTrace()
//                            Jesli chcemy coś scastowac to klasy na serverze i cliencie muszą być w tych samych package! (i muszą być takie same)
            Toast.makeText(applicationContext, "Decoder exception! Dawid left you some notes about it. ", Toast.LENGTH_SHORT).show()
        }
//
//        userList = listOf(User("Dawid Tracz", "d.tk@gmail.com"), User("Aga Klim", "klk.mun@gmail.com"), User("Ada Ficek", "ada.ficek@gmail.com"),
//                User("Dawid Traczko", "d.sdsdtk@gmail.com"), User("Aga sadsaKlim", "klksads.mun@gmail.com"), User("sdAda Ficek", "adakoko.ficek@gmail.com"),
//                User("Dawid Traczsss", "dxs.tk@xssgmail.com"), User("sssAga Klim", "klk.mun@gmail.cxxom"), User("Ada Ficekxx", "ada.ficekxxx@gmailsss.com"),
//                User("Dawid cxzTraczsss", "dxs.tk@xssgmail.comzcx"), User("sssAga Klimszcs", "klksks.mun@gmail.cxxom"), User("Ada Ficekxzsadsax", "adsksa.ficekxxx@gmailsss.com"))

        for (u in userList){
            friendsList.add(u.username)
        }
        friendsListView = findViewById(R.id.friendsList)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, friendsList)
        friendsListView.adapter = adapter
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
}
