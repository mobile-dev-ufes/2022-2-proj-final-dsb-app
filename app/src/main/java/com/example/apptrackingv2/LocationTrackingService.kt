package com.example.apptrackingv2
import android.app.Service
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast

class LocationTrackingService : Service() {
    private lateinit var locationManager: LocationManager
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            // Do something with the location updates, e.g. send it to a server or store it locally
//            Toast.makeText(applicationContext, "Latitude: " + location.latitude + "\nLongitude: " + location.longitude, Toast.LENGTH_SHORT).show()

            val intent = Intent("location_update")
            intent.putExtra("latitude", location.latitude)
            intent.putExtra("longitude", location.longitude)
            intent.putExtra("speed", location.speed)
            sendBroadcast(intent)
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    override fun onCreate() {
        super.onCreate()
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0f, locationListener)
        } catch (e: SecurityException) {
            // Handle error
        }
        return START_STICKY
    }

    override fun onDestroy() {
        locationManager.removeUpdates(locationListener)
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
