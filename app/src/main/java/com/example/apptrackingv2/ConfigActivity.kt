package com.example.dsb_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dsb_app.databinding.ActivityConfigBinding


class ConfigActivity: AppCompatActivity() {

    private lateinit var binding: ActivityConfigBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}