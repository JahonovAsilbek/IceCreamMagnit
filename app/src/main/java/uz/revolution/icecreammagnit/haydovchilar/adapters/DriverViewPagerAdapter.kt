package uz.revolution.icecreammagnit.haydovchilar.adapters

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.haydovchilar.DriverItemFragment
import uz.revolution.icecreammagnit.models.Driver

class DriverViewPagerAdapter(var driverList:List<Driver>,frm:FragmentManager):FragmentStatePagerAdapter(frm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int =2

    override fun getItem(position: Int): Fragment {

        val bundle = Bundle()
        bundle.putInt("param1", position)
        val fragment = DriverItemFragment()
        fragment.arguments = bundle
        return fragment
    }
}