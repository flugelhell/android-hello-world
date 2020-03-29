package com.flugelhell.helloworld

import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_map.*
import kotlin.math.roundToInt


class MapActivity : AppCompatActivity() {
    /* Menggunakan FusedLocationProviderClient karena pake 2 service yaitu FINE dan COARSE Location */
    lateinit var mFusedLocationClient: FusedLocationProviderClient

    var currentLatitude: Double = 0.0
    var currentLongitude: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        btnGetLocation.setOnClickListener {
            getLastLocation()
//            val gmmIntentUri: Uri = Uri.parse("geo:${currentLatitude},${currentLongitude}?q=${currentLatitude},${currentLongitude}(This Location)")
//            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
//            mapIntent.setPackage("com.google.android.apps.maps")
//            startActivity(mapIntent)
        }

        btnCalculateDistance.setOnClickListener{
            calculateDistance()
        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun getLastLocation() {
        if (isLocationEnabled()) {

            mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                var location: Location? = task.result
                if (location == null) {
                    requestNewLocationData()
                } else {
                    currentLatitude = location.latitude
                    currentLongitude = location.longitude


                    edtLat.setText(currentLatitude.toString())
                    edtLong.setText(currentLongitude.toString())
                }
            }
        } else {
            Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }

    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        /* Jika Tidak Real Time set 0 */
        mLocationRequest.interval = 2000 // Update Interval 2 Detik
        /* Jika Tidak Real Time set 0 */
        mLocationRequest.fastestInterval = 0 // Update Minimal Interval 0 Detik
        /* Agar Tidak Real Time Update Uncomment Line Dibawah */
        /* mLocationRequest.numUpdates = 1 */

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            currentLatitude = mLastLocation.latitude
            currentLongitude = mLastLocation.longitude

            edtLat.setText(currentLatitude.toString())
            edtLong.setText(currentLongitude.toString())
        }
    }

    private fun calculateDistance(){
        val startPoint = Location("My Location")
        startPoint.latitude = currentLatitude
        startPoint.longitude = currentLongitude

        val endPoint = Location("Attendance Location")
        endPoint.latitude = -6.1390118
        endPoint.longitude = 106.8618275

        var distance = FloatArray(1)
        Location.distanceBetween(startPoint.latitude,startPoint.longitude,endPoint.latitude,endPoint.longitude, distance)

        txtPerkiraanJarak.text = String.format("%.2f", distance[0]) + " Meter" // 2 angka dibelakang koma

        val gmmIntentUri: Uri = Uri.parse("geo:${endPoint.latitude}, ${endPoint.longitude}?q=${endPoint.latitude},${endPoint.longitude},(Your Destination) ")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }
}
