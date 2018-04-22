package com.coding.team.meetpin.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.coding.team.meetpin.R
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class NewPinActivity : AppCompatActivity() {
    lateinit var messageBox : EditText
    lateinit var pickDateButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_pin)
        messageBox = findViewById(R.id.message)
        pickDateButton = findViewById(R.id.pickDateButton)


        fun pickDate(view : View)
        {

        }
        pickDateButton.setOnClickListener {
//            val intent : Intent = Intent(applicationContext, PickDateActivity::class.java)
//            startActivity(intent)

        }
    }
}
