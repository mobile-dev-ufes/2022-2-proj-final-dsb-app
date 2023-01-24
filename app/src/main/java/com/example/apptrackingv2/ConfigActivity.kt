package com.example.dsb_app

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.apptrackingv2.LocationTrackingService
import com.example.dsb_app.databinding.ActivityConfigBinding

class ConfigActivity: AppCompatActivity() {

    private lateinit var binding: ActivityConfigBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.switchButton.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked) {
                startTrackingService()
            } else {
                stopTrackingService()
            }
        }
    }

    private val locationReceiver = object : BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context?, intent: Intent?) {
            val latitude = intent?.getDoubleExtra("latitude", 0.0)
            val longitude = intent?.getDoubleExtra("longitude", 0.0)
            // Atualize a interface de usuário com as informações de localização
            binding.textoCoordenadas.text = "Lat: $latitude\nLong: $longitude"
        }
    }

    private fun startTrackingService() {
        val serviceIntent = Intent(this, LocationTrackingService::class.java)
        startService(serviceIntent)
        val filter = IntentFilter("location_update")
        registerReceiver(locationReceiver, filter)
    }

    private fun stopTrackingService() {
        val serviceIntent = Intent(this, LocationTrackingService::class.java)
        stopService(serviceIntent)
        unregisterReceiver(locationReceiver)
        binding.textoCoordenadas.text = ""
    }

}