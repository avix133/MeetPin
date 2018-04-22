package com.coding.team.meetpin.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import com.coding.team.meetpin.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var pinButton : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_2)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map2) as SupportMapFragment

        mapFragment.getMapAsync(this)
        pinButton = findViewById(R.id.pinButton)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val cracow = LatLng(50.06, 19.94)
        mMap.addMarker(MarkerOptions().position(cracow).title("Marker in Cracow"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cracow, 15f))
    }
}
