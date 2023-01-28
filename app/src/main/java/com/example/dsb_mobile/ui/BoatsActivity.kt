package com.example.dsb_mobile.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dsb_mobile.databinding.ActivityBoatsBinding
import com.example.dsb_mobile.databinding.ActivityConfigBinding

class BoatsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBoatsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoatsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
