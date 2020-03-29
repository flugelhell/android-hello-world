package com.flugelhell.helloworld

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class SplashScreen : AppCompatActivity() {
    /**
     * Untuk ganti icon klik kanan di app terus tambah asset image
     * Untuk menambah image copy paste ke folder drawable dan pilih hanya drawable
     */
    private val myPermissionsRequestCamera = 1
    private val myPermissionRequestFineLocation = 20
    private val myPermissionRequestCoarseLocation = 21
    private val listOfPermissionRequested = arrayOf(
        arrayOf<String>(Manifest.permission.CAMERA, myPermissionsRequestCamera.toString()),
        arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION, myPermissionRequestFineLocation.toString()),
        arrayOf<String>(Manifest.permission.ACCESS_COARSE_LOCATION, myPermissionRequestCoarseLocation.toString())
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        /* Request all Permissions Nanti taro di landing page aja*/
        for(row in listOfPermissionRequested){
            cekAndAskPermission(row[0].toString(), row[1].toInt())
        }
        /* Cek is phone rooted ?
         * Cek the connection is established ?
         * Cek is user logged and session still active ?
         */
        /* Wait for 2 second */
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            /* Disabled back button */
            finish()
        }, 2000)
    }

    /* permissionRequested = Manifest.permission.CAMERA */
    private fun cekAndAskPermission(permissionRequested: String, requestCode: Int){
        if (ContextCompat.checkSelfPermission(this, permissionRequested)
            != PackageManager.PERMISSION_GRANTED) {
            /* Permission is not granted and then ask permission */
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(this,
                    permissionRequested)
                != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        permissionRequested)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(this,
                        arrayOf(permissionRequested),
                        requestCode)

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                // Permission has already been granted
            }
        }
    }
}
