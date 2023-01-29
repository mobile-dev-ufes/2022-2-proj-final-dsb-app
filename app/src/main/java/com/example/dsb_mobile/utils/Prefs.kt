package com.example.dsb_mobile.utils

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {
    val PREFS_FILENAME = "com.example.dsb_mobile.utils.prefs"
    val TRACKING_SWITCH = "tracking_switch"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    var trackingSwitch: Boolean
        get() = prefs.getBoolean(TRACKING_SWITCH, false)
        set(value) = prefs.edit().putBoolean(TRACKING_SWITCH, value).apply()
}