package com.example.dsb_mobile.viewmodel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dsb_mobile.data.model.GPSValues
import com.google.android.gms.location.*

/**
 * A ViewModel class that provides the last GPSValues using MutableLiveData.
 * @property lastGPSValues A MutableLiveData instance that holds the last GPSValues.
 * @property fusedLocationProvider A FusedLocationProviderClient instance for location services.
 * @property locationRequest A LocationRequest object to set the location update parameters.
 * @property locationCallback A LocationCallback object that receives location updates.
 */
class LocationViewModel : ViewModel() {
    var lastGPSValues = MutableLiveData<GPSValues?>()
    private var fusedLocationProvider: FusedLocationProviderClient? = null
    private val locationRequest: LocationRequest = LocationRequest.create().apply {
        interval = 30
        fastestInterval = 1
        priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        maxWaitTime = 60
    }
    private var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val locationList = locationResult.locations
            if (locationList.isNotEmpty()) {
                //The last location in the list is the newest
                val location = locationList.last()
                val newGPSValues = GPSValues(location.latitude, location.longitude, location.speed)
                lastGPSValues.value = newGPSValues
            }
        }
    }

    /**
     * Method to start location updates.
     * @param context A Context instance.
     */
    fun startLocationUpdates(context: Context) {
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(context)

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProvider?.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    /**
     * Method to stop location updates.
     * @param context A Context instance.
     */
    fun stopLocationUpdates(context: Context) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProvider?.removeLocationUpdates(locationCallback)
        }
    }
}