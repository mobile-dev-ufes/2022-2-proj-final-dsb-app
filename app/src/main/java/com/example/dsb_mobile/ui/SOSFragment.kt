package com.example.apptrackingv2.views

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dsb_mobile.R
import com.example.dsb_mobile.databinding.SosFragmentBinding

/**
 * Fragment class to handle SOS feature, a button that blinks and call for help when clicked
 */
class SOSFragment : Fragment() {

    private lateinit var binding: SosFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SosFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupData()

        /**
         * Set on click listener for the SOS button to change background color
         */
        var isRunning = false
        val buttonSos = binding.buttonSos
        binding.buttonSos.setOnClickListener {
            if (!isRunning) {
                isRunning = true
                val colors = intArrayOf(Color.RED, Color.WHITE)
                var index = 0
                val handler = Handler()
                val runnable = object : Runnable {
                    override fun run() {
                        buttonSos.setBackgroundColor(colors[index % colors.size])
                        buttonSos.setTextColor(colors[(index + 1) % colors.size])
                        view.setBackgroundColor(colors[(index + 1) % colors.size])
                        index++
                        if (isRunning) {
                            handler.postDelayed(this, 1000)
                        }

                    }
                }
                handler.post(runnable)
            } else {
                isRunning = false

            }


        }
    }

    /**
     * Method to setup the data for the fragment
     */
    private fun setupData() {
        binding.sosText.text = getString(R.string.first_fragment)
    }


}
