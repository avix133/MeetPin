package com.coding.team.meetpin.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.coding.team.meetpin.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val cracow = LatLng(50.06, 19.94)
        mMap.addMarker(MarkerOptions().position(cracow).title("Marker in Cracow"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cracow, 15f))


        mMap.setOnMarkerClickListener(this)
    }


    override fun onMarkerClick(p0: Marker?): Boolean {
        val intent = Intent(this, PinWindowActivity::class.java)
        startActivity(intent)
        return false
    }
}
