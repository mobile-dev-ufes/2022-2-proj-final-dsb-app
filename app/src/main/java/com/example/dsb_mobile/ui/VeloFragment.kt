package com.example.apptrackingv2.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.dsb_mobile.databinding.VeloFragmentBinding
import com.example.dsb_mobile.viewmodel.LocationViewModel
import java.util.*

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
            val originalSpeed = it?.speed.toString()
            val desiredPrecision = 2
            val formatter = "%.${desiredPrecision}f"
            val locale = Locale("en", "US")
            val formattedSpeed = String.format(locale, formatter, originalSpeed.toFloat())
            binding.textVelo.text = formattedSpeed + " nós"
        })


    }
}