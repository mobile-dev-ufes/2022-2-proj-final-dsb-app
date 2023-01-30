package com.example.dsb_mobile.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.dsb_app.ViewPagerAdapter
import com.example.dsb_mobile.R
import com.example.dsb_mobile.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

/**
 * The MainActivity class is responsible for displaying the tabs for the different screens in the app.
 * It also handles the navigation to different screens and the inflating of the layout.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) // mantem a tela sempre ligada

        // carrega as telas e o layout
        setupTabLayout()
        setupViewPager()


    }

    /**
     * The onCreateOptionsMenu function is used to create the options menu (Teams, Settings).
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item1 -> {
                // Going to the settings screen, but needs to login first
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.menu_item2 -> {
                // Going to boat's competition list
                val intent = Intent(this, BoatsActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    /**
     * The setupViewPager function is used to set up the navetagion by TAB.
     */
    private fun setupViewPager() {
        binding.viewPager.apply {
            adapter = ViewPagerAdapter(supportFragmentManager, binding.tabLayout.tabCount)
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))
        }
    }

    /**
     * The setupTabLayout function is used to set up the tab layout.
     */
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
