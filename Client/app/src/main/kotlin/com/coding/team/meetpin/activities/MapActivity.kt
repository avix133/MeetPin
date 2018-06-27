package com.coding.team.meetpin.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Point
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.DisplayMetrics
import android.widget.Toast
import com.coding.team.meetpin.R
import com.coding.team.meetpin.client_server.netty.ClientHandler
import com.coding.team.meetpin.dao.model.Pin
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.concurrent.TimeUnit

class MapActivity : MenuActivity(),
        OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnCameraIdleListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnMyLocationClickListener {

    companion object {
        private const val PLACE_PICKER_REQUEST = 3
    }

    private lateinit var mMap: GoogleMap
    private var maxY: Int = 0
    private var maxX: Int = 0
    private val PERSENTAGE_POS_X = 0.1
    private val PERSENTAGE_POS_Y = 0.83
    private lateinit var address: List<Address>
    private lateinit var mOption: MarkerOptions
    private lateinit var marker: Marker
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mLastKnownLocation: Location
    // Default location (Cracow, Poland)
    private val mDefaultZoom = 15.0f
    private val mDefaultLocation = LatLng(50.06, 19.94)
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private var mLocationPermissionGranted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map
                ) as SupportMapFragment

        mapFragment.getMapAsync(this)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        maxY = displayMetrics.heightPixels
        maxX = displayMetrics.widthPixels
        //println( maxX )
        //println( maxX )
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            loadPlacePicker()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val cracow = LatLng(50.06, 19.94)
        getLocationPermission()
        getUserLocation()
        displayAllPins()

        mMap.setOnMyLocationButtonClickListener(this)
        mMap.setOnMyLocationClickListener(this)


//        mMap.addMarker(MarkerOptions().position(cracow).title("Marker in Cracow"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cracow, 15f))

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
        intent.putExtra("MARKER_ID", p0!!.snippet)
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
        val intent:Intent = Intent(applicationContext, NewPinActivity::class.java)
        intent.putExtra("FROM_ACTIVITY", "MapActivity")
        intent.putExtra("ADDRESS", address.get(0).getAddressLine(0))
        intent.putExtra( "LATITUDE", p0!!.position.latitude)
        intent.putExtra( "LONGITUDE", p0!!.position.longitude)
        startActivity(intent)
//        Toast.makeText(applicationContext, address.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show()
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
        return mMap.projection.fromScreenLocation(Point((PERSENTAGE_POS_X * maxX).toInt(), (PERSENTAGE_POS_Y * maxY).toInt()))
        //return mMap.projection.fromScreenLocation(Point(MARKER_POS_X, maxY - MARKER_POS_Y))
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

    @SuppressLint("MissingPermission")
    private fun getUserLocation() {
        if (mLocationPermissionGranted) {
            val userLocation = mFusedLocationProviderClient.lastLocation
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
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gpsEnabled: Boolean = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!gpsEnabled) {

            var dialog = AlertDialog.Builder(this)
                    .setMessage("Enable Your Location")
                    .setPositiveButton("Location Settings", DialogInterface.OnClickListener { paramDialogInterface, paramInt ->
                        val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        startActivity(myIntent)
                    })
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { paramDialogInterface, paramInt -> })

            dialog.show()
        } else {
            mLocationPermissionGranted = true
        }
    }

    private fun loadPlacePicker() {
        val builder = PlacePicker.IntentBuilder()

        try {
            startActivityForResult(builder.build(this@MapActivity), PLACE_PICKER_REQUEST)
        } catch (ex: GooglePlayServicesRepairableException) {
            ex.printStackTrace()
        } catch (ex: GooglePlayServicesNotAvailableException) {
            ex.printStackTrace()
        }
    }

    private fun changeMapPosition(location: LatLng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlacePicker.getPlace(this, data)
                val toastMsg = String.format("%s", place.name)
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show()
                changeMapPosition(place.latLng)

            }
        }
    }

    private fun displayAllPins() {
        var result: List<Pin> = emptyList()

        try {
            result = ClientHandler.getInstance().getDisplayPins().get(5, TimeUnit.SECONDS).payload as List<Pin>
        } catch (ex: Exception) {
            println(ex.printStackTrace())
        }

        for ( value in result.listIterator()) {
            println(value)
            val pin = LatLng(value.map_latitude, value.map_longitude)

            marker = mMap.addMarker(MarkerOptions()
                    .position(pin)
                    .title(value.message)
                    .snippet(value.id.toString())
            )

            if (value.pinToGlobal != null) {
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}


