package com.example.dsb_mobile.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.dsb_mobile.ui.SOSFragment
import com.example.dsb_mobile.ui.VeloFragment

/**
 * ViewPagerAdapter to handle fragments for a view pager
 *
 * @property fm FragmentManager for the adapter
 * @property tabCount Number of tabs
 *
 * @constructor Creates a new ViewPagerAdapter for a tab UI
 */
class ViewPagerAdapter(fm: FragmentManager, var tabCount: Int) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> SOSFragment()
            1 -> VeloFragment()
            else -> SOSFragment()
        }
    }

    override fun getCount(): Int {
        return tabCount
    }

    /**
     * Returns the title for the tab at a given position
     * @param position position of the tab
     * @return title for the tab at the given position
     */
    override fun getPageTitle(position: Int): CharSequence {
        return "Tab " + (position + 1)
    }
}
