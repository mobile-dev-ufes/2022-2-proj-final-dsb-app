package com.example.apptrackingv2.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.dsb_mobile.databinding.VeloFragmentBinding
import com.example.dsb_mobile.viewmodel.LocationViewModel

/**
 * A Fragment that displays the speed from GPS location updates.
 */
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

        // Instantiates the LocationViewModel
        locationModel.startLocationUpdates(requireContext())

        // Observes the MutableLiveData lastGPSValues and updates the UI
        locationModel.lastGPSValues.observe(viewLifecycleOwner, Observer {
            binding.label.text = it?.speed.toString()
        })
    }
}