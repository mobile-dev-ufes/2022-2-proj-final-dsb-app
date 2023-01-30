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

/**
 * Activity that handles the configuration of the application.
 *
 * @constructor Creates an instance of the [ConfigActivity] class.
 */
class ConfigActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfigBinding

    /**
     * [LocationViewModel] instance to manage the GPS updates.
     */
    private val locationModel = LocationViewModel()

    /**
     * [ConfigPreferences] instance to handle the user's information about what team was selected.
     */
    private lateinit var preferences: ConfigPreferences

    /**
     * Called when the activity is created.
     *
     * Inflates the layout, sets the [switchButton] listener to start/stop GPS updates, sets the [spinner] item selected listener to save the user's team preference and sets the result of the permission request.
     *
     * @param savedInstanceState Bundle containing the data it most recently supplied in [onSaveInstanceState] if the activity is being re-initialized after previously being shut down.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //When switch is moved, request the location and speed of the boat
        binding.switchButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (TrackingUtility.requestLocationPermission(this)) {
                    locationModel.startLocationUpdates(this)
                    // Look the MutableLiveData lastGPSValues and update the UI
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

        preferences = ConfigPreferences(this) //setting the context of the preference

        // Set an OnItemSelectedListener for the spinner
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            // When an item is selected, update the preference and TextView with the selected item

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                preferences.setString("TEAM", selectedItem)
                binding.testPreference.text = selectedItem
            }

            // Empty implementation for when no item is selected (needed to use "object" class)
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == Constants.REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "Permissões de GPS ok", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(applicationContext, "Permissões negadas", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
