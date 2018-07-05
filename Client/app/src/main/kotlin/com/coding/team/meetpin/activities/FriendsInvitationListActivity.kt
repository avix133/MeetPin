package com.coding.team.meetpin.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.coding.team.meetpin.R
import com.coding.team.meetpin.client_server.netty.ClientHandler
import com.coding.team.meetpin.dao.model.User
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class FriendsInvitationListActivity: MenuActivity() {

    lateinit var friendsButton : Button
    lateinit var userList : List<User>
    lateinit var invitationListView : ListView
    private var invitationList : MutableList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invitation_friends_list)

        friendsButton = findViewById(R.id.friendsListButton)

        friendsButton.setOnClickListener( {
            val intent = Intent(applicationContext, FriendsListActivity::class.java)
            intent.putExtra("FROM_ACTIVITY", "FriendsInvitationListActivity")
            startActivity(intent)
        })
        userList = listOf<User>()
        println("Sending: ")
        try {
            userList = ClientHandler.getInstance().getPendingInvitations().get(5, TimeUnit.SECONDS).payload as List<User>

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(applicationContext, "Something went wrong... ", Toast.LENGTH_SHORT).show()
        }

        for (u in userList) {
            invitationList.add(u.username)
        }
        invitationListView = findViewById(R.id.invitationList)
        val adapter = MyListAdapter(this, R.layout.invitation_friend, invitationList)
        invitationListView.adapter = adapter

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private class MyListAdapter(context: Context, var resource: Int, var objects: MutableList<String>) : ArrayAdapter<String>(context, resource, objects) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater: LayoutInflater = LayoutInflater.from(context)
            val view: View = layoutInflater.inflate(resource, null)
            val name: TextView = view.findViewById(R.id.friendName)
            val button: Button = view.findViewById(R.id.invitationButton)

            name.text = objects[position]

            button.setOnClickListener {
                Toast.makeText(context, "Request accepted!!!", Toast.LENGTH_LONG).show()

                try {
                    ClientHandler.getInstance().acceptFriendRequest(name.text.toString()).get(5, TimeUnit.SECONDS).payload.toString()

                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(context, "Something went wrong... ", Toast.LENGTH_SHORT).show()
                }
            }

            return view
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}