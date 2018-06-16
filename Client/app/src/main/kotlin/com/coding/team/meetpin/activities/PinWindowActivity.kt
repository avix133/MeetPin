package com.coding.team.meetpin.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.widget.ImageView
import android.widget.TextView
import com.coding.team.meetpin.R
import com.coding.team.meetpin.client_server.netty.ClientHandler
import com.coding.team.meetpin.dao.model.Pin
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

class PinWindowActivity : AppCompatActivity() {

    lateinit var owner_label : TextView
    lateinit var owner_text : TextView
    lateinit var owner_image : ImageView
    lateinit var location_label : TextView
    lateinit var location_text : TextView
    lateinit var date_label : TextView
    lateinit var date_text : TextView
    lateinit var participants_label : TextView

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_pin_popup_window)

        val dm = DisplayMetrics() //dm = displayMetrics
        windowManager.defaultDisplay.getMetrics(dm)

        val width = dm.widthPixels
        val height = dm.heightPixels

        window.setLayout((width * .8).toInt(), (height * .4).toInt()) //pass .8 and .4 as val

        owner_label = findViewById(R.id.pinned_by_label)
        owner_text = findViewById(R.id.organizer_name_label)
        owner_image = findViewById(R.id.organizer_image_view)
        location_label = findViewById(R.id.location_label)
        location_text = findViewById(R.id.location_text_view)
        date_label = findViewById(R.id.date_label)
        date_text = findViewById(R.id.date_text_view)
        participants_label = findViewById(R.id.participants_label)

        val pinId: String? = intent.getStringExtra("MARKER_ID")
        println("Pin and marker id: $pinId")

//        val result: List<Pin> = ClientHandler.getInstance().getDisplayPins().get(5, TimeUnit.SECONDS).payload as List<Pin>

        try {
            val result: Pin = ClientHandler.getInstance().getPinData(pinId!!.toInt()).get(5, TimeUnit.SECONDS).payload as Pin
            val res = SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(result.meeting_date)
            date_text.text = res
        } catch (ex : NullPointerException) {

        }

    }
}