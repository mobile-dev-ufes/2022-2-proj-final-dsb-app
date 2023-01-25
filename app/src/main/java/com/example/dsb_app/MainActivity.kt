package com.example.dsb_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.dsb_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide() //tira barra feia

        binding.buttonSosTest.setOnClickListener(this)
        binding.buttonSettingsTest.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.button_sos_test) {
            //Toast.makeText(applicationContext, "SHOW SOS", Toast.LENGTH_SHORT).show()
            val intent=Intent(this, SosActivity::class.java)
            startActivity(intent)
        } else if (view.id == R.id.button_settings_test) {
            Toast.makeText(applicationContext, "SHOW SETTINGS", Toast.LENGTH_SHORT).show()
        }

    }
}



