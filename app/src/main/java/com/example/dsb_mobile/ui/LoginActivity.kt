package com.example.dsb_mobile.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dsb_mobile.databinding.ActivityLoginBinding

/**
 * Login Activity to handle login and navigation to ConfigActivity
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * Set on click listener for the login button to navigate to ConfigActivity
         */
        binding.btnLogin.setOnClickListener {
            val password = "dsb123"
            val passwordTyped = binding.input.text
            if(passwordTyped.toString() == password){
                val intent = Intent(this, ConfigActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(applicationContext, "Senha incorreta!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}