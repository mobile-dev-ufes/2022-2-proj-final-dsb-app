package com.example.dsb_mobile.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * Utility class for checking and requesting the location permissions
 */
object TrackingUtility {
    /**
     * Requests location permissions if not already granted
     * @param context Context of the calling activity
     * @return boolean value indicating if permission is already granted
     */
    fun requestLocationPermission(context: Context): Boolean {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                Constants.REQUEST_CODE
            )
            return false
        }
        return true
    }
}