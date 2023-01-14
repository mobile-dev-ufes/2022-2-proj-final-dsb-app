package com.example.apptrackingv2.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.location.Location
import android.os.*
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.*
import io.socket.client.Socket
import org.greenrobot.eventbus.EventBus
import com.example.apptrackingv2.R

class MyBackgroundService : Service() {

    companion object {
        private val CHANNEL_ID = "channel_01"
        private val PACKAGE_NAME = "com.example.apptrackingv2.services"
        private val EXTRA_STARTED_FROM_NOTIFICATION = "$PACKAGE_NAME.started_from_notification"
        private val UPDATE_INTERVAL_IN_MIL : Long = 0
        private val FASTED_UPDATE_INTERVAL_IN_MIL : Long = UPDATE_INTERVAL_IN_MIL/2
        private val NOTIFICATION_ID = 1234
    }

    private val mBinder = LocalBinder()

    inner class LocalBinder: Binder(){
        internal val service: MyBackgroundService
            get() = this@MyBackgroundService
    }

    private var mChangingConfiguration = false
    private var mNotificationManager: NotificationManager? = null
    private var locationRequest: LocationRequest? = null
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var locationCallback: LocationCallback? = null
    private var mServiceHandler: Handler? = null
    private var mLocation: Location? = null

    private var mSocket: Socket? = null

    private val notification: Notification
        get() {
            val intent = Intent(this, MyBackgroundService::class.java)
            val text = Common.getLocationText(mLocation)
//            mSocket?.emit("newinfo", text)
            intent.putExtra(EXTRA_STARTED_FROM_NOTIFICATION, true)
            val servicePendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val activityPendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val builder = NotificationCompat.Builder(this)
                .addAction(R.drawable.ic_baseline_launch_24, "Launch", activityPendingIntent)
                .addAction(R.drawable.ic_baseline_cancel_24, "Cancel", servicePendingIntent)
                .setContentText(text)
                .setContentTitle(Common.getLocationTitle(this))
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(text)
                .setWhen(System.currentTimeMillis())
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                builder.setChannelId(CHANNEL_ID)
            return builder.build()
        }

    override fun onCreate() {
        // init socket to run in background
        SocketHandler.setSocket()
        SocketHandler.establishConnection()

        mSocket = SocketHandler.getSocket()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        locationCallback = object: LocationCallback(){
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                onNewLocation(p0!!.lastLocation)
            }
        }

        createLocationRequest()
        getLastLocation()

        val handlerThread = HandlerThread("EDNTDev")
        handlerThread.start()
        mServiceHandler = Handler(handlerThread.looper)
        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = packageName
            val mChannel = NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT)
            mNotificationManager!!.createNotificationChannel(mChannel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val startedFromNotification = intent!!.getBooleanExtra(EXTRA_STARTED_FROM_NOTIFICATION, false)
        if(startedFromNotification){
            removeLocationUpdates()
            stopSelf()
        }
        return Service.START_NOT_STICKY
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mChangingConfiguration = true
    }

    fun removeLocationUpdates() {
        try {
            fusedLocationProviderClient!!.removeLocationUpdates((locationCallback!!))
            Common.setRequestingLocationUpdates(this, false)
            stopSelf()
        }catch (ex: SecurityException){
            Common.setRequestingLocationUpdates(this, true)
            Log.e("EDMTDev", "Lost location permission. Could not remove update. $ex")
        }
    }

    private fun getLastLocation() {
        try {
            fusedLocationProviderClient!!.lastLocation
                .addOnCompleteListener { task ->
                    if(task.isSuccessful && task.result != null)
                        mLocation = task.result
                    else
                        Log.e("EDMTDEV", "Failed to get location")
                }
        }catch (ex: SecurityException) {
            Log.e("EDMTDEV", ""+ex.message)
        }
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest!!.interval = UPDATE_INTERVAL_IN_MIL
        locationRequest!!.fastestInterval = FASTED_UPDATE_INTERVAL_IN_MIL
        locationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun onNewLocation(lastLocation: Location?) {
        mLocation = lastLocation!!
        val text = Common.getLocationText(mLocation)
        mSocket?.emit("newinfo", text)
        EventBus.getDefault().postSticky(BackgroundLocation(mLocation!!))
        if(serviceIsRunningInForeground(this))
            mNotificationManager!!.notify(NOTIFICATION_ID, notification)
    }

    private fun serviceIsRunningInForeground(context: Context): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for(service in manager.getRunningServices(Integer.MAX_VALUE)){
            if(javaClass.name.equals(service.service.className))
                if(service.foreground)
                    return true
        }
        return false
    }

    override fun onBind(intent: Intent): IBinder {
        stopForeground(true)
        mChangingConfiguration = false
        return mBinder
    }

    override fun onRebind(intent: Intent?) {
        stopForeground(true)
        mChangingConfiguration = false
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        if(!mChangingConfiguration && Common.requestingLocationUpdates(this))
            startForeground(NOTIFICATION_ID, notification)
        return true
    }

    override fun onDestroy() {
        mServiceHandler!!.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    fun requestLocationUpdates(){
        Common.setRequestingLocationUpdates(this, true)
        startService(Intent(applicationContext, MyBackgroundService::class.java))
        try {
            fusedLocationProviderClient!!.requestLocationUpdates(locationRequest!!, locationCallback!!, Looper.myLooper())
        }catch (ex: SecurityException){
            Common.setRequestingLocationUpdates(this, false)
            Log.e("EDMTDev", "Lost location permission. $ex")
        }
    }
}