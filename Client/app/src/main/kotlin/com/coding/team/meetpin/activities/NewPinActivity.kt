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
import com.coding.team.meetpin.client_server.netty.ClientHandler
import com.coding.team.meetpin.client_server.request.RequestType
import com.coding.team.meetpin.client_server.request.impl.AuthenticationRequest
import com.coding.team.meetpin.client_server.request.impl.FriendListRequest
import com.coding.team.meetpin.client_server.response.impl.DefaultResponse
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.android.synthetic.main.activity_new_pin.datePickerDialogBox
import java.util.*
import java.util.concurrent.Future
import javax.xml.datatype.DatatypeConstants.MONTHS

class NewPinActivity : AppCompatActivity() {
    lateinit var messageBox : EditText
    lateinit var placeBox : TextView
    lateinit var pickDateButton : Button
    lateinit var getTextButton : Button
    lateinit var pickFriendButton : Button
    lateinit var submitButton : Button
    lateinit var getTextTextView : EditText
    lateinit var location : String
    lateinit var author : GoogleSignInAccount
    private var authorId = -1
    lateinit var friendsList : Future<DefaultResponse>
    lateinit var userList : List<User>
    private var emailList : MutableList<String> = arrayListOf()
    private var invitedFriends : MutableSet<String> = hashSetOf()
    lateinit var fDialog: Dialog
    lateinit var submitFriends : Button
    lateinit var vList : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_pin)
        pickDateButton = findViewById(R.id.pickDateButton)
        messageBox = findViewById(R.id.message)
        pickFriendButton = findViewById(R.id.pickFriendButton)
        getTextButton = findViewById(R.id.getTextButton)
        placeBox = findViewById(R.id.placeBox)
        submitButton = findViewById(R.id.submitButton)
        getTextTextView = findViewById(R.id.getTextTextView)
        author = GoogleSignIn.getLastSignedInAccount(this)!!
        userList = listOf(User("Dawid Tracz", "d.tk@gmail.com"), User("Aga Klim", "klk.mun@gmail.com"), User("Ada Ficek", "ada.ficek@gmail.com") )
//        Toast.makeText(applicationContext, author?.email.toString(), Toast.LENGTH_SHORT).show()

        authorId = AuthenticationRequest(author?.email.toString()).clientId

        if(intent.extras != null)
            location = intent.getStringExtra("ADDRESS")

        placeBox.text = location
        fDialog = Dialog(this)
//        @SuppressLint("SetTextI18n")


        pickDateButton.setOnClickListener {
            pickDate(this.findViewById(android.R.id.content))
        }

        getTextButton.setOnClickListener {
            getTextTextView.text = messageBox.text
        }
        pickFriendButton.setOnClickListener{
//            emailList = mutableListOf()
            for (u in userList){
                emailList.add(u.email)
            }
            showFriends(this.findViewById(android.R.id.content))
            Toast.makeText(applicationContext, "jeszcze w secie", Toast.LENGTH_SHORT).show()
//            showFriends(this)

//            friendsList = ClientHandler.getInstance().friendList
//            Toast.makeText(applicationContext, friendsList.toString(), Toast.LENGTH_SHORT).show()

        }
        submitButton.setOnClickListener{

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
    fun showFriends(view: View){
        Toast.makeText(applicationContext, emailList.toString(), Toast.LENGTH_SHORT).show()
        fDialog.setContentView(R.layout.friends_list)
        submitButton = fDialog.findViewById(R.id.fSubmitButton)
        vList = findViewById(R.id.listView)
        val adapter = ArrayAdapter<String>(this, R.layout.one_friend, R.id.friendCheck, emailList)
        vList.setAdapter(adapter)
        vList.onItemClickListener = object: AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val itemValue = vList.getItemAtPosition(position) as CheckBox
                if(itemValue.isChecked()){
                    invitedFriends.add(itemValue.text.toString())
                }else{
                    invitedFriends.remove(itemValue.text)
                }
            }

        }
        submitFriends.setOnClickListener{
            fDialog.dismiss()
        }
        fDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        fDialog.show()
    }
}




