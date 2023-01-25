package com.example.dsb_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dsb_app.databinding.ActivitySettingsBinding

class SettingsActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.recyclerTeams.layoutManager = LinearLayoutManager(this)
        setContentView(binding.root)

        //supportActionBar?.hide() //tira barra feia
    }
/*
    override fun onClick(view: View) {


    }*/
}