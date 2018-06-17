package com.coding.team.meetpin.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.coding.team.meetpin.R
import com.coding.team.meetpin.app.User
import com.coding.team.meetpin.client_server.request.impl.AuthenticationRequest
import com.coding.team.meetpin.client_server.response.impl.DefaultResponse
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.android.synthetic.main.activity_new_pin.datePickerDialogBox
import java.text.FieldPosition
import java.util.*
import java.util.concurrent.Future

class NewPinActivity : AppCompatActivity() {
    lateinit var messageBox : EditText
    lateinit var placeBox : TextView
    lateinit var pickDateButton : Button
    lateinit var getTextButton : Button
    lateinit var pickFriendButton : Button
    lateinit var submitButton : Button
    lateinit var getTextTextView : EditText
    lateinit var pickedFriends : TextView
    lateinit var location : String
    lateinit var author : GoogleSignInAccount
    private var authorId = -1
    lateinit var friendsList : Future<DefaultResponse>
    lateinit var userList : List<User>
    private var invitedFriends : MutableSet<String> = hashSetOf()
    lateinit var fDialog: Dialog
    lateinit var submitFriendsButton : Button
    lateinit var vList : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_pin)
        pickDateButton = findViewById(R.id.pickDateButton)
        messageBox = findViewById(R.id.message)
        pickFriendButton = findViewById(R.id.pickFriendButton)
        getTextButton = findViewById(R.id.getTextButton)
        placeBox = findViewById(R.id.placeBox)
        pickedFriends = findViewById(R.id.pickedFriend)
        submitButton = findViewById(R.id.submitButton)
        getTextTextView = findViewById(R.id.getTextTextView)
        author = GoogleSignIn.getLastSignedInAccount(this)!!
        userList = listOf(User("Dawid Tracz", "d.tk@gmail.com"), User("Aga Klim", "klk.mun@gmail.com"), User("Ada Ficek", "ada.ficek@gmail.com"),
                User("Dawid Traczko", "d.sdsdtk@gmail.com"), User("Aga sadsaKlim", "klksads.mun@gmail.com"), User("sdAda Ficek", "adakoko.ficek@gmail.com"),
                User("Dawid Traczsss", "dxs.tk@xssgmail.com"), User("sssAga Klim", "klk.mun@gmail.cxxom"), User("Ada Ficekxx", "ada.ficekxxx@gmailsss.com"),
                User("Dawid cxzTraczsss", "dxs.tk@xssgmail.comzcx"), User("sssAga Klimszcs", "klksks.mun@gmail.cxxom"), User("Ada Ficekxzsadsax", "adsksa.ficekxxx@gmailsss.com"))

        authorId = AuthenticationRequest(author?.email.toString()).clientId

        if(intent.extras != null)
            location = intent.getStringExtra("ADDRESS")

        placeBox.text = location
        fDialog = Dialog(this)



        pickDateButton.setOnClickListener {
            pickDate(this.findViewById(android.R.id.content))
        }

        getTextButton.setOnClickListener {
            getTextTextView.text = messageBox.text
        }
        pickFriendButton.setOnClickListener{
            val emailList : MutableList<String> = arrayListOf()
            for (u in userList){
                emailList.add(u.email)
            }
            showFriends(this.findViewById(android.R.id.content), emailList)

        }
        submitButton.setOnClickListener{
            //addPin: dayOfMonth,  monthOfYear, year
            //          invitedFriend
            //place : intent.getStringExtra("LATITUDE") intent.getStringExtra("LONGITUDE")
            //message
            //authorId
            val intent: Intent = Intent(applicationContext, MapActivity::class.java)
            intent.putExtra("FROM_ACTIVITY", "NewPinActivity")
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    fun pickDate(view : View)
    {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        val datePickerDialog = DatePickerDialog(this, android.R.style.Theme_Holo_Dialog, DatePickerDialog.OnDateSetListener { datePicker, year, monthOfYear, dayOfMonth ->
            datePickerDialogBox.text = " " + dayOfMonth + "." + monthOfYear + "." + year
        },year, month, day)
        datePickerDialog.show()
    }
    fun showFriends(view: View, emailList: MutableList<String>){
//        Toast.makeText(applicationContext, emailList.toString(), Toast.LENGTH_SHORT).show()
        fDialog.setContentView(R.layout.friends_list)
        submitFriendsButton = fDialog.findViewById(R.id.submitFriendsButton)
        vList = fDialog.findViewById(R.id.listView)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, emailList)
        vList.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        vList.setAdapter(adapter)


        submitFriendsButton.setOnClickListener{
            var selected = ""
            val cntChoise= vList.count
            val sparseBooleanArray = vList.checkedItemPositions
            for (i:Int in 0..(cntChoise-1))
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






