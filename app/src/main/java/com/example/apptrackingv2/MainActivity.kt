package com.example.apptrackingv2

import android.Manifest
import android.content.*
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.preference.PreferenceManager
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.apptrackingv2.databinding.ActivityMainBinding
import com.example.apptrackingv2.services.BackgroundLocation
import com.example.apptrackingv2.services.Common
import com.example.apptrackingv2.services.MyBackgroundService
import com.google.android.material.tabs.TabLayout
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.DexterError
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_config.*
import kotlinx.android.synthetic.main.sos_fragment.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        if(p1.equals(Common.KEY_REQUEST_LOCATION_UPDATE))
            setButtonState(p0!!.getBoolean(Common.KEY_REQUEST_LOCATION_UPDATE, false))
    }


    private lateinit var binding: ActivityMainBinding

    private var mService: MyBackgroundService?=null
    private var mBound = false
    private val mServiceConnection = object: ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val binder = p1 as MyBackgroundService.LocalBinder
            mService = binder.service
            mBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            mService = null
            mBound = false
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun onBackgroundLocationRetrieve(event: BackgroundLocation)
    {
        if(event.location != null){
            val newInfoLocation = Common.getLocationText(event.location)
            Toast.makeText(this, newInfoLocation, Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) // mantem a tela sempre ligada

        // carrega as telas e o layout
        setupTabLayout()
        setupViewPager()

        // fica de olho se clicou no botao de config
        binding.configButton.setOnClickListener{
            val intent = Intent(this, ConfigActivity::class.java)
            startActivity(intent)
        }

//        val switchButton = findViewById(R.id.switch_button) as Switch

        Dexter.withActivity(this) // below line is use to request the number of permissions which are required in our app.
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                Manifest.permission.FOREGROUND_SERVICE
            ) // after adding permissions we are calling an with listener method.
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport) {

                    button_sos.setOnClickListener{
                        Toast.makeText(applicationContext, "SOS", Toast.LENGTH_SHORT).show()
                    }

//                    switch_button.setOnCheckedChangeListener { buttonView, isChecked ->
//                        if (isChecked) {
//                            mService!!.requestLocationUpdates()
//                            Toast.makeText(applicationContext, "ON", Toast.LENGTH_SHORT).show()
//                        } else {
//                            mService!!.removeLocationUpdates()
//                            Toast.makeText(applicationContext, "OFF", Toast.LENGTH_SHORT).show()
//                        }
//                    }

                    setButtonState(Common.requestingLocationUpdates(this@MainActivity))
                    bindService(Intent(this@MainActivity,MyBackgroundService::class.java),
                        mServiceConnection,
                        Context.BIND_AUTO_CREATE)
                }

                override fun onPermissionRationaleShouldBeShown(
                    list: List<PermissionRequest>,
                    permissionToken: PermissionToken
                ) {
                    // this method is called when user grants some permission and denies some of them.
                    permissionToken.continuePermissionRequest()
                }
            }).withErrorListener { error: DexterError? ->
                // we are displaying a toast message for error message.
                Toast.makeText(applicationContext, "Error occurred! ", Toast.LENGTH_SHORT)
                    .show()
            } // below line is use to run the permissions on same thread and to check the permissions
            .onSameThread().check()

    }

    private fun setButtonState(boolean: Boolean) {

    }

    override fun onStart() {
        super.onStart()
        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        PreferenceManager.getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(this)
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Clique em VOLTAR novamente para sair", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }


    // funcoes responsaveis pela navegacao das telas pelo TAB
    private fun setupViewPager() {
        binding.viewPager.apply {
            adapter = ViewPagerAdapter(supportFragmentManager, binding.tabLayout.tabCount)
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))
        }
    }

    private fun setupTabLayout() {
        binding.tabLayout.apply {
            addTab(this.newTab().setText("SOS"))
            addTab(this.newTab().setText("Velô"))

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.position?.let {
                        binding.viewPager.currentItem = it
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })
        }
    }

}