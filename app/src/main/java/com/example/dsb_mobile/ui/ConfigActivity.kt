package com.example.dsb_mobile.ui

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.dsb_mobile.databinding.ActivityConfigBinding
import com.example.dsb_mobile.utils.Constants
import com.example.dsb_mobile.utils.TrackingUtility
import com.example.dsb_mobile.viewmodel.LocationViewModel

class ConfigActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfigBinding
    private val locationModel = LocationViewModel()
    private lateinit var preferences: ConfigPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.switchButton.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked) {
                if(TrackingUtility.requestLocationPermission(this)){
                    locationModel.startLocationUpdates(this)
                    // Observa o MutableLiveData lastGPSValues e atualiza a UI
                    locationModel.lastGPSValues.observe(this, Observer {
                        binding.textoCoordenadas.text = "lat: " + it?.latitude +
                                    "\nlong: " + it?.longitude +
                                    "\nspeed: " + it?.speed
                    })
                }
            } else {
                binding.textoCoordenadas.text = "GPS desligado"
                locationModel.stopLocationUpdates(this)
            }
        }
        preferences=ConfigPreferences(this) //salvar dados de user

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                preferences.setString("TEAM",selectedItem)
            binding.testPreference.text=selectedItem
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }




    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == Constants.REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "Permissões de GPS ok", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Permissões negadas", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
