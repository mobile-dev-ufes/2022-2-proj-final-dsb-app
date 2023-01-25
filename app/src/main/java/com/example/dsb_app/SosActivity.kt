package com.example.dsb_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.widget.Toast
import com.example.dsb_app.databinding.ActivityMainBinding
import com.example.dsb_app.databinding.ActivitySosBinding

class SosActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySosBinding
   // var toggle=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySosBinding.inflate(layoutInflater)
        setContentView(binding.root)


       // binding.buttonSos.setOnClickListener(this)
    }

//    override fun onClick(view: View) {
     //  Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),)
  //  }
}



