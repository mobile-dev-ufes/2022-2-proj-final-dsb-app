package com.example.apptrackingv2

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.apptrackingv2.databinding.ActivityConfigBinding
import com.example.apptrackingv2.databinding.ActivityMainBinding


class ConfigActivity: AppCompatActivity() {

    private lateinit var binding: ActivityConfigBinding
    private var boatNumber: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.switchButton.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                Toast.makeText(applicationContext, "ON", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(applicationContext, "OFF", Toast.LENGTH_SHORT).show()
//            }
//        }
        val users = arrayOf(
            "Henrique Lage",
            "Arariboia",
            "Leviatã",
            "Zênite",
            "Reis do Sol",
            "Solares",
            "Sete Capitães",
            "Solaris",
            "MSP",
            "Vento Sul",
            "Escola Naval",
            "Fernando Amorim"
        )
        val spin = findViewById<View>(R.id.spinner) as Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, users)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.adapter = adapter

        spin?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                boatNumber = position
                Toast.makeText(applicationContext, users[position], Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun getStatusSwitch(): Boolean{
        return binding.switchButton.isChecked
    }

    fun setStatusSwitch(status: Boolean) {
        Toast.makeText(applicationContext, status.toString(), Toast.LENGTH_SHORT).show()
    }

    fun getNumberOfBoat():Int {
        return boatNumber
    }
}