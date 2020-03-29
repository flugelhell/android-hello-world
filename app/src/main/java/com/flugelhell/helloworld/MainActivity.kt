package com.flugelhell.helloworld

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val resultCodeQRCODE = 0x991

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* Button Listener */
        btnSet.setOnClickListener{
            val text = edtInput.text.toString()
            setView(text)
        }

        /* Button QR Code Scanner */
        btnQRScanner.setOnClickListener{
            startActivityForResult(Intent(this, CodeScannerActivity::class.java), resultCodeQRCODE)
        }

        /* Button Map */
        btnMap.setOnClickListener{
            startActivity(Intent(this, MapActivity::class.java))
        }
    }

    private fun setView(text: String){
        txtHasil.text = text
    }

    /* Override method onActivityResult */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // First we need to check if the requestCode matches the one we used.
        if(requestCode == resultCodeQRCODE) {

            // The resultCode is set by the AnotherActivity
            // By convention RESULT_OK means that what ever
            // AnotherActivity did was successful
            if(resultCode == Activity.RESULT_OK) {
                // Get the result from the returned Intent

                // Use the data - in this case, display it in a Toast.
                txtQRHasil.text = data?.getStringExtra("qr_decode")
            }
        }
    }
}
