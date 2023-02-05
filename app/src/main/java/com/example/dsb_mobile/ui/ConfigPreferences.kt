package com.example.dsb_mobile.ui

import android.content.Context
import android.content.SharedPreferences

/**
 * Class to handle shared preferences operations
 *
 * @param context: Context of the application
 */
class ConfigPreferences(context: Context) {
    private val sp: SharedPreferences =
        context.getSharedPreferences("TEAM", Context.MODE_PRIVATE)

    /**
     * Function to set a team chosen in shared preferences
     *
     * @param key: Key of the shared preferences item
     * @param str: a team
     */
    fun setString(key: String, str: String) {
        sp.edit().putString(key, str).apply()
    }

    /**
     * Function to retrieve a team from shared preferences
     *
     * @param key: Key of the shared preferences item
     * @return: Team name or an empty string if not found
     */
    fun getString(key: String): String {
        return sp.getString(key, "") ?: ""
    }
}