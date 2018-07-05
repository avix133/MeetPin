package com.coding.team.meetpin.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.coding.team.meetpin.R
import com.coding.team.meetpin.client_server.netty.ClientHandler
import com.coding.team.meetpin.client_server.request.impl.AuthenticationRequest
import com.coding.team.meetpin.client_server.response.impl.DefaultResponse
import com.coding.team.meetpin.dao.model.Pin
import com.coding.team.meetpin.dao.model.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.android.synthetic.main.activity_new_pin.datePickerDialogBox
import java.sql.Date
import java.sql.Timestamp
import java.util.*
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class NewPinActivity : AppCompatActivity() {
    lateinit var placeBox: TextView
    lateinit var pickDateButton: Button
    lateinit var pickGlobalCheckbox: CheckBox
    lateinit var pickFriendButton: Button
    lateinit var submitButton: Button
    lateinit var pickedFriends: TextView
    lateinit var messageBox: TextView
    lateinit var location: String
    lateinit var authorDisplayed: TextView
    lateinit var author: GoogleSignInAccount
    private var authorId = -1
    lateinit var friendsList: Future<DefaultResponse>
    lateinit var userList: List<User>
    private var invitedFriends: MutableSet<String> = hashSetOf()
    lateinit var fDialog: Dialog
    lateinit var submitFriendsButton: Button
    lateinit var vList: ListView
    private var isGlobalPin: Boolean = false
    lateinit var datePickerDialog: DatePickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_pin)
        pickDateButton = findViewById(R.id.pickDateButton)
        pickFriendButton = findViewById(R.id.pickFriendButton)
        placeBox = findViewById(R.id.placeBox)
        pickedFriends = findViewById(R.id.pickedFriend)
        submitButton = findViewById(R.id.submitButton)
        pickGlobalCheckbox = findViewById(R.id.globalPin)
        authorDisplayed = findViewById(R.id.organizer_name_label)
        messageBox = findViewById(R.id.messageBox)
        author = GoogleSignIn.getLastSignedInAccount(this)!!

        authorId = AuthenticationRequest(author?.email.toString()).clientId
        authorDisplayed.text = authorId.toString()
        if (intent.extras != null)
            location = intent.getStringExtra("ADDRESS")

        placeBox.text = location
        fDialog = Dialog(this)


        pickGlobalCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            isGlobalPin = isChecked
        }
        pickDateButton.setOnClickListener {
            pickDate(this.findViewById(android.R.id.content))
        }

        pickFriendButton.setOnClickListener {

            try {
                userList = ClientHandler.getInstance().getFriendList().get(5, TimeUnit.SECONDS).payload as List<User>
                showFriends(this.findViewById(android.R.id.content), userList)
            } catch (e: Exception) {

            }

        }
        submitButton.setOnClickListener {
            //addPin: dayOfMonth,  monthOfYear, year
            //          invitedFriend
            //place : intent.getStringExtra("LATITUDE") intent.getStringExtra("LONGITUDE")
            //message
            //authorId
            Toast.makeText(applicationContext, datePickerDialog.toString(), Toast.LENGTH_SHORT).show()
            val expire = Calendar.getInstance()
            expire.add(Calendar.DATE, +7)
            try {
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.YEAR, datePickerDialog.datePicker.year)
                calendar.set(Calendar.MONTH,datePickerDialog.datePicker.month)
                calendar.set(Calendar.DAY_OF_MONTH, datePickerDialog.datePicker.dayOfMonth)
                ClientHandler.getInstance().addPin(Pin(messageBox.text.toString(),
                        User("", ""),
                        intent.getDoubleExtra("LATITUDE", 0.0),
                        intent.getDoubleExtra("LONGTITUDE", 0.0),
                        java.sql.Date(calendar.timeInMillis),
                        java.sql.Date(calendar.timeInMillis)))
            } catch (e: Exception) {
                e.printStackTrace()
            }


            val intent: Intent = Intent(applicationContext, MapActivity::class.java)
            intent.putExtra("FROM_ACTIVITY", "NewPinActivity")
            startActivity(intent)
        }
    }


    @SuppressLint("SetTextI18n")
    fun pickDate(view: View) {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        datePickerDialog = DatePickerDialog(this, android.R.style.Theme_Holo_Dialog, DatePickerDialog.OnDateSetListener { datePicker, year, monthOfYear, dayOfMonth ->
            datePickerDialogBox.text = " " + dayOfMonth + "." + monthOfYear + "." + year
        }, year, month, day)
        datePickerDialog.show()
    }

    fun showFriends(view: View, userList: List<User>) {
//        Toast.makeText(applicationContext, emailList.toString(), Toast.LENGTH_SHORT).show()
        val emailList: MutableList<String> = arrayListOf()
        for (u in userList) {
            emailList.add(u.email)
        }
        fDialog.setContentView(R.layout.friends_list)
        submitFriendsButton = fDialog.findViewById(R.id.submitFriendsButton)
        vList = fDialog.findViewById(R.id.listView)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, emailList)
        vList.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        vList.setAdapter(adapter)


        submitFriendsButton.setOnClickListener {
            var selected = ""
            val cntChoise = vList.count
            val sparseBooleanArray = vList.checkedItemPositions
            for (i: Int in 0..(cntChoise - 1))
                if (sparseBooleanArray.get(i))
                    selected += vList.getItemAtPosition(i).toString() + "\n"
//                    invitedFriends.add(vList.getItemAtPosition(i).toString() )
//            pickedFriends.text = invitedFriends.toString()
            pickedFriends.text = selected
//            Toast.makeText(fDialog.context, invitedFriends.toString(), Toast.LENGTH_SHORT).show()
            fDialog.dismiss()
        }
        fDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        fDialog.show()
    }

}






