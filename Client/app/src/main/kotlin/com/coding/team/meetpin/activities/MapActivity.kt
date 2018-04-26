package com.coding.team.meetpin.activities

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.coding.team.meetpin.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    // Default location (Cracow, Poland)
    private val mDefaultZoom = 15.0f
    private val mDefaultLocation = LatLng(50.06, 19.94)
    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    private var mLocationPermissionGranted: Boolean = false
    private lateinit var mMap: GoogleMap
    private lateinit var mfusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mLastKnownLocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_maps)

        mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        getLocationPermission()
        getUserLocation()

        mMap.setOnMyLocationButtonClickListener(this)
        mMap.setOnMyLocationClickListener(this)
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE)

        } else {
            mMap.isMyLocationEnabled = true
        }
    }

    private fun getUserLocation() {
        if(mLocationPermissionGranted) {

            val userLocation = mfusedLocationProviderClient.lastLocation
            userLocation.addOnCompleteListener(this, { task ->
                if (task.isSuccessful) {
                    mLastKnownLocation = task.result
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            LatLng(mLastKnownLocation.latitude,
                                    mLastKnownLocation.longitude), mDefaultZoom))
                } else {
                    mMap.uiSettings.isMyLocationButtonEnabled = false
                }
            })
        } else {
            mMap.moveCamera(CameraUpdateFactory
                    .newLatLngZoom(mDefaultLocation, mDefaultZoom))
            mMap.addMarker(MarkerOptions().position(mDefaultLocation).title("Marker in Cracow"))
        }
    }

     override fun onMyLocationClick(location: Location) {
        Toast.makeText(this, "Current location:\n$location", Toast.LENGTH_LONG).show()
    }

    override fun onMyLocationButtonClick(): Boolean {
        enableLocationServices()
        return false
    }

    private fun enableLocationServices() {
        val locationManager  = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gpsEnabled : Boolean =  locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if(!gpsEnabled ) {

            var dialog = AlertDialog.Builder(this)
                    .setMessage("Enable Your Location")
                    .setPositiveButton("Location Settings", DialogInterface.OnClickListener {
                        paramDialogInterface, paramInt ->
                        val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        startActivity(myIntent)
                    })
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                        paramDialogInterface, paramInt -> })

            dialog.show()
        } else {
            mLocationPermissionGranted = true
        }
    }

}
