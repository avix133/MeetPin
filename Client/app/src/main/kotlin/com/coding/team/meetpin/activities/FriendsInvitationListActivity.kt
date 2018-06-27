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
    lateinit var returnButton : Button
    lateinit var friendsButton : Button
    lateinit var userList : List<User>
    lateinit var invitationListView : ListView
    private var invitationList : MutableList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invitation_friends_list)

        returnButton = findViewById(R.id.returnButton)
        friendsButton = findViewById(R.id.friendsListButton)
        returnButton.setOnClickListener( {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra("FROM_ACTIVITY", "FriendsListActivity")
            startActivity(intent)
        })
        friendsButton.setOnClickListener( {
            val intent = Intent(applicationContext, FriendsListActivity::class.java)
            intent.putExtra("FROM_ACTIVITY", "FriendsInvitationListActivity")
            startActivity(intent)
        })
        userList = listOf<User>()
        println("Sending: ")
        try {
            val future = ClientHandler.getInstance().pendingInvitations
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
//        userList = listOf(User("Dawid Tracz", "d.tk@gmail.com"), User("Aga Klim", "klk.mun@gmail.com"), User("Ada Ficek", "ada.ficek@gmail.com"),
//                User("Dawid Traczko", "d.sdsdtk@gmail.com"), User("Aga sadsaKlim", "klksads.mun@gmail.com"), User("sdAda Ficek", "adakoko.ficek@gmail.com"),
//                User("Dawid Traczsss", "dxs.tk@xssgmail.com"), User("sssAga Klim", "klk.mun@gmail.cxxom"), User("Ada Ficekxx", "ada.ficekxxx@gmailsss.com"),
//                User("Dawid cxzTraczsss", "dxs.tk@xssgmail.comzcx"), User("sssAga Klimszcs", "klksks.mun@gmail.cxxom"), User("Ada Ficekxzsadsax", "adsksa.ficekxxx@gmailsss.com"))

        for (u in userList){
            invitationList.add(u.username)
        }
        invitationListView = findViewById(R.id.invitationList)
        val adapter = MyListAdapter(this, R.layout.invitation_friend, invitationList)
        invitationListView.adapter = adapter
    }

    private class MyListAdapter(context: Context, var resource: Int, var objects: MutableList<String>) : ArrayAdapter<String>(context, resource, objects) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater: LayoutInflater = LayoutInflater.from(context)
            val view : View = layoutInflater.inflate(resource, null)
            var name: TextView = view.findViewById(R.id.friendName)
            val button: Button = view.findViewById(R.id.invitationButton)

            name.text = objects[position]

            button.setOnClickListener {
                Toast.makeText(context, name.text, Toast.LENGTH_LONG).show()

            }

            return view
        }
    }
}