package shelterfind.android.example.com.shelterfind.shelterfind.adaptors

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.View
import shelterfind.android.example.com.shelterfind.shelterfind.AllFragment
import shelterfind.android.example.com.shelterfind.shelterfind.HomeFilterFragment
import shelterfind.android.example.com.shelterfind.shelterfind.HostelFragment
import shelterfind.android.example.com.shelterfind.shelterfind.HotelFragment

open class CustomPagerAdaptor(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {

    private val NUM_ITEMS = 4
    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 // Fragment # 0 - This will show FirstFragment
            -> AllFragment.newInstance()
            1 // Fragment # 0 - This will show FirstFragment different title
            -> HostelFragment.newInstance()
            2 // Fragment # 1 - This will show SecondFragment
            -> HotelFragment.newInstance()
            3 // Fragment # 1 - This will show SecondFragment
            -> HomeFilterFragment.newInstance()
            else -> null
        }
    }

    override fun getCount(): Int {
        return NUM_ITEMS
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "All"
            1 -> "Hostel"
            2 -> "Hotel"
            3 -> "Home"
            else-> null
        }

    }
}