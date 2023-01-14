package com.example.apptrackingv2.services

import android.content.Context
import android.location.Location
import android.preference.PreferenceManager
import java.text.DateFormat
import java.util.*

object Common {

    val KEY_REQUEST_LOCATION_UPDATE = "requesting_location_update"

    fun getLocationText(location: Location?):String {
        return if(location == null)
            "Unknown location"
        else
            "${location.latitude} / ${location.longitude}"
    }

    fun getLocationTitle(context: Context): String {
        return String.format("Location Updated: ${DateFormat.getDateInstance().format(Date())}")
    }

    fun setRequestingLocationUpdates(context: Context, value: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putBoolean(KEY_REQUEST_LOCATION_UPDATE, value)
            .apply()
    }

    fun requestingLocationUpdates(context: Context): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(context)
            .getBoolean(KEY_REQUEST_LOCATION_UPDATE, false)
    }
}