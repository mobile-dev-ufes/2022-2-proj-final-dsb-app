package com.example.apptrackingv2.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.apptrackingv2.R
import com.example.apptrackingv2.databinding.VeloFragmentBinding

class VeloFragment : Fragment() {

    private lateinit var binding: VeloFragmentBinding

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
    }

    private fun setupData() {
        binding.label.text = getString(R.string.second_fragment)
    }
}
