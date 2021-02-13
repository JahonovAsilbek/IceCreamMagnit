package uz.revolution.icecreammagnit.mijozlar.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import uz.revolution.icecreammagnit.mijozlar.CustomerItemFragment
import uz.revolution.icecreammagnit.mijozlar.MijozlarFragment

class CategoryAdapter(
    var customerList: ArrayList<MijozlarFragment.Category>,
    fragmentManager: FragmentManager
) :
    FragmentStatePagerAdapter(
        fragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {

    override fun getCount(): Int = customerList.size

    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putSerializable("customer", customerList[position].arrayList)
        val fragment = CustomerItemFragment()
        fragment.arguments = bundle
        return fragment
    }


}