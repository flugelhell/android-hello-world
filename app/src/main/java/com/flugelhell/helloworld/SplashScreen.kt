package com.flugelhell.helloworld

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreen : AppCompatActivity() {
    /**
     * Untuk ganti icon klik kanan di app terus tambah asset image
     * Untuk menambah image copy paste ke folder drawable dan pilih hanya drawable
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        /* Wait for 2 second */
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            /* Disabled back button */
            finish()
        }, 2000)
    }
}
