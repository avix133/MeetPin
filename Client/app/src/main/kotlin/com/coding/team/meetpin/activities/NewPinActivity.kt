package com.coding.team.meetpin.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.coding.team.meetpin.R
import kotlinx.android.synthetic.main.activity_new_pin.datePickerDialogBox
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class NewPinActivity : AppCompatActivity() {
    lateinit var messageBox : EditText
    lateinit var placeBox : TextView
    lateinit var pickDateButton : Button
    lateinit var getTextButton : Button
    lateinit var pickFriendButton : Button
    lateinit var getTextTextView : EditText
    lateinit var location : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_pin)
        pickDateButton = findViewById(R.id.pickDateButton)
        messageBox = findViewById(R.id.message)
        pickFriendButton = findViewById(R.id.pickFriendButton)
        getTextButton = findViewById(R.id.getTextButton)
        placeBox = findViewById(R.id.placeBox)
        getTextTextView = findViewById(R.id.getTextTextView)
        if(intent.extras != null)
            location = intent.getStringExtra("ADDRESS")

        location
        placeBox.text = location
//        @SuppressLint("SetTextI18n")


        pickDateButton.setOnClickListener {
            pickDate(this.findViewById(android.R.id.content))
        }

        getTextButton.setOnClickListener {
            getTextTextView.text = messageBox.text
        }
        pickFriendButton.setOnClickListener{

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
            datePickerDialogBox.text = " Chosen Date:\n " + dayOfMonth + "." + monthOfYear + "." + year
        },year, month, day)
        datePickerDialog.show()
    }
}




