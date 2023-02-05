package com.example.dsb_mobile.ui

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dsb_mobile.databinding.SosFragmentBinding
import com.example.dsb_mobile.viewmodel.LocationViewModel
import com.github.nkzawa.socketio.client.IO
import androidx.lifecycle.Observer
import com.example.dsb_mobile.data.model.AppData
import java.util.*

/**
 * Fragment class to handle SOS feature, a button that blinks and call for help when clicked
 */
class SOSFragment : Fragment() {

    private lateinit var binding: SosFragmentBinding
    private val locationModel = LocationViewModel()

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

//        val socket = IO.socket("http://192.168.15.43:4000")
        val socket = IO.socket("http://server-solares-solaris.herokuapp.com")
        socket.connect()

        // Instantiates the LocationViewModel
        locationModel.startLocationUpdates(requireContext())

        // Observes the MutableLiveData lastGPSValues and updates the UI
        locationModel.lastGPSValues.observe(viewLifecycleOwner, Observer {
            if(AppData.statusTracking){
                val resultGPS = "[0%s,%.6f,%.6f,%.2f,%s];01/01/99 00:41:02"
                    .format(Locale.US,
                        AppData.numberBoat,
                        it?.latitude ?: 0.0,
                        it?.longitude ?: 0.0,
                        it?.speed ?: 0.0,
                        if (isRunning) "1" else "0")
                println(resultGPS)
                socket.emit("newinfo", resultGPS)
            }
        })
    }


}
