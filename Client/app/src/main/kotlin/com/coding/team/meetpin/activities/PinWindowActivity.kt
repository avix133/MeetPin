package com.coding.team.meetpin.activities

import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.coding.team.meetpin.R
import com.coding.team.meetpin.client_server.netty.ClientHandler
import com.coding.team.meetpin.dao.model.Pin
import com.coding.team.meetpin.dao.model.User
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

class PinWindowActivity : MenuActivity() {

    private lateinit var ownerLabel : TextView
    private lateinit var ownerText : TextView
    private lateinit var ownerImage : ImageView
    private lateinit var locationLabel : TextView
    private lateinit var locationText : TextView
    private lateinit var dateLabel : TextView
    private lateinit var dateText : TextView
    private lateinit var participantsLabel : TextView
    private lateinit var beThereButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_pin_popup_window)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width = dm.widthPixels
        val height = dm.heightPixels

        window.setLayout((width * .8).toInt(), (height * .4).toInt()) //pass .8 and .4 as val

        ownerLabel = findViewById(R.id.pinned_by_label)
        ownerText = findViewById(R.id.organizer_name_label)
        ownerImage = findViewById(R.id.organizer_image_view)
        locationLabel = findViewById(R.id.location_label)
        locationText = findViewById(R.id.location_text_view)
        dateLabel = findViewById(R.id.date_label)
        dateText = findViewById(R.id.date_text_view)
        participantsLabel = findViewById(R.id.participants_label)
        beThereButton = findViewById(R.id.be_there_button)

        val pinId: String? = intent.getStringExtra("MARKER_ID")

        try {
            val result: Pin = ClientHandler.getInstance().getPinData(pinId!!.toInt()).get(5, TimeUnit.SECONDS).payload as Pin

            beThereButton.setOnClickListener{
                ClientHandler.getInstance().acceptEvent(result.id)
            }
            setPinOwner(result)
            setMeetingDate(result)
            setLocation(result)
        } catch (ex: Exception) {
            println("Pin data requested but not existent for this user")
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun setMeetingDate(result: Pin) {
            val fTimestamp = SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(result.meeting_date)
            dateText.text = fTimestamp
    }

    private fun setLocation(result: Pin) {
        val lat = result.map_latitude
        val lon = result.map_longitude
        val res = "$lat $lon"
        locationText.text = res
    }

    private fun setPinOwner(result: Pin) {
        val owner = result.user.email
        ownerText.text = owner.toString()
    }
}