package com.example.apptrackingv2.views

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dsb_app.R
import com.example.dsb_app.databinding.VeloFragmentBinding

class VeloFragment : Fragment() {

    private lateinit var binding: VeloFragmentBinding

    private val locationReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val speed = intent?.getDoubleExtra("speed", 0.0)
            binding.label.text = speed.toString() + "nós"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = VeloFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupData()
        registerReceiver()
    }

    private fun registerReceiver() {
        val filter = IntentFilter("location_update")
        context?.registerReceiver(locationReceiver, filter)
    }

    private fun setupData() {
        binding.label.text = getString(R.string.second_fragment)
    }

    override fun onStop() {
        super.onStop()
        context?.unregisterReceiver(locationReceiver)
    }
}
