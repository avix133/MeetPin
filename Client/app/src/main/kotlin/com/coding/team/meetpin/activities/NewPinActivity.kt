package com.coding.team.meetpin.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.coding.team.meetpin.R
import kotlinx.android.synthetic.main.activity_new_pin.datePickerDialogBox
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class NewPinActivity : AppCompatActivity() {
    lateinit var messageBox : EditText
    lateinit var pickDateButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_pin)
        pickDateButton = findViewById(R.id.pickDateButton)
        messageBox = findViewById(R.id.message)


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

        pickDateButton.setOnClickListener {
            pickDate(this.findViewById(android.R.id.content))
        }
//        messageBox.get
        }


    }

