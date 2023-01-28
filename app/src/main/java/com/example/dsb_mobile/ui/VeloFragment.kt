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
import androidx.lifecycle.Observer
import com.example.dsb_mobile.databinding.VeloFragmentBinding
import com.example.dsb_mobile.utils.TrackingUtility
import com.example.dsb_mobile.viewmodel.LocationViewModel

class VeloFragment : Fragment() {

    private lateinit var binding: VeloFragmentBinding
    private val locationModel = LocationViewModel()

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

        // Instancia o objeto LocationViewModel
        locationModel.startLocationUpdates(requireContext())

        // Observa o MutableLiveData lastGPSValues e atualiza a UI
        locationModel.lastGPSValues.observe(viewLifecycleOwner, Observer {
            binding.label.text = it?.speed.toString()
        })
    }
}