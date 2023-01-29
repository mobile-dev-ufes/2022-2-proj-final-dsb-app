package com.example.dsb_mobile.ui

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.dsb_mobile.R
import com.example.dsb_mobile.databinding.ActivityConfigBinding
import com.example.dsb_mobile.utils.Constants
import com.example.dsb_mobile.utils.Prefs
import com.example.dsb_mobile.utils.TrackingUtility
import com.example.dsb_mobile.viewmodel.LocationViewModel

class ConfigActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfigBinding
    private val locationModel = LocationViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = Prefs(this)
        binding.switchButton.isChecked = prefs.trackingSwitch

        binding.switchButton.setOnCheckedChangeListener{ _, isChecked ->
//            prefs.trackingSwitch = isChecked
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
                Toast.makeText(applicationContext, "GPS desligado...", Toast.LENGTH_SHORT).show()
                binding.textoCoordenadas.text = "GPS desligado"
                locationModel.stopLocationUpdates(this)
            }
        }


        val users = arrayOf(
            "Henrique Lage",
            "Arariboia",
            "Leviatã",
            "Zênite",
            "Reis do Sol",
            "Solares",
            "Sete Capitães",
            "Solaris",
            "MSP",
            "Vento Sul",
            "Escola Naval",
            "Fernando Amorim"
        )
        val spin = findViewById<View>(R.id.spinner) as Spinner
        val adapter = ArrayAdapter(this, androidx.preference.R.layout.support_simple_spinner_dropdown_item, users)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.adapter = adapter

        spin?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(applicationContext, users[position], Toast.LENGTH_SHORT).show()
            }

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
