package com.flugelhell.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* Button Listener */
        btnSet.setOnClickListener{
            val text = edtInput.text.toString()
            setView(text)
        }
    }

    private fun setView(text: String){
        txtHasil.text = text
    }
}
