package com.example.dsb_mobile.ui

import android.content.Context
import android.content.SharedPreferences

class ConfigPreferences(context: Context) {
    private val sp: SharedPreferences =
        context.getSharedPreferences("TEAM", Context.MODE_PRIVATE)

    fun setString(key: String, str: String) {
        sp.edit().putString(key, str).apply()
    }

    fun getString(key: String): String {
        return sp.getString(key, "") ?: ""
    }
}