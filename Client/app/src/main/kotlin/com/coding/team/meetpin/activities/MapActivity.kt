package com.coding.team.meetpin.activities

import android.content.Intent
import android.graphics.Point
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.widget.Toast
import com.coding.team.meetpin.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMarkerDragListener, GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraMoveStartedListener {


    private lateinit var mMap: GoogleMap
    private var maxY: Int = 0
    private var maxX: Int = 0
    private val MARKER_POS_X = 80
    private val MARKER_POS_Y = 180
    private lateinit var address: List<Address>
    private lateinit var mOption: MarkerOptions
    private lateinit var marker: Marker


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        maxY = displayMetrics.heightPixels
        maxX = displayMetrics.widthPixels

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val cracow = LatLng(50.06, 19.94)
        mMap.addMarker(MarkerOptions().position(cracow).title("Marker in Cracow"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cracow, 15f))
        mOption = MarkerOptions().position(getMarkerPosition()).draggable(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        marker = mMap.addMarker(mOption)
        mMap.setOnMarkerDragListener(this)
        mMap.setOnCameraMoveStartedListener(this)
        mMap.setOnCameraMoveListener(this)
        mMap.setOnCameraIdleListener(this)


        mMap.setOnMarkerClickListener(this)
    }


    override fun onMarkerClick(p0: Marker?): Boolean {
        val intent = Intent(this, PinWindowActivity::class.java)
        intent.putExtra("FROM_ACTIVITY", "MapActivity")
        startActivity(intent)
        return false
    }

    override fun onMarkerDragStart(p0: Marker?) {
    }

    override fun onMarkerDrag(p0: Marker?) {
    }

    override fun onMarkerDragEnd(p0: Marker?) {
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        marker = mMap.addMarker(mOption)
        marker.position = getMarkerPosition()
        val geocoder = Geocoder(applicationContext)
        address = geocoder.getFromLocation(p0!!.position.latitude, p0.position.longitude, 1)
        Toast.makeText(applicationContext, address.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show()
    }

    override fun onCameraMoveStarted(p0: Int) {
        marker.position = getMarkerPosition()
    }

    override fun onCameraMove() {
        marker.position = getMarkerPosition()
    }

    override fun onCameraIdle() {
        marker.position = getMarkerPosition()
    }

    private fun getMarkerPosition(): LatLng {
        return mMap.projection.fromScreenLocation(Point(MARKER_POS_X, maxY - MARKER_POS_Y))
    }

}


