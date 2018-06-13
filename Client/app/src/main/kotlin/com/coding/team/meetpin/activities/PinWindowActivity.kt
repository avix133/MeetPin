package com.coding.team.meetpin.activities

import android.os.Bundle
import android.util.DisplayMetrics
import com.coding.team.meetpin.R

class PinWindowActivity : MenuActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_pin_popup_window)

        val dm = DisplayMetrics() //dm = displayMetrics
        windowManager.defaultDisplay.getMetrics(dm)

        val width = dm.widthPixels
        val height = dm.heightPixels

        window.setLayout((width * .8).toInt(), (height * .4).toInt()) //pass .8 and .4 as val
    }
}