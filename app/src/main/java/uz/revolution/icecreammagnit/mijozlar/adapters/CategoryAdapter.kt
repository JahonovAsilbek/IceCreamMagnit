package uz.revolution.icecreammagnit.mijozlar.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import uz.revolution.icecreammagnit.mijozlar.CustomerItemFragment
import uz.revolution.icecreammagnit.mijozlar.MijozlarFragment
import uz.revolution.icecreammagnit.models.Customer

class CategoryAdapter(
    fragmentManager: FragmentManager
) :
    FragmentStatePagerAdapter(
        fragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {

    private var customerList: ArrayList<Customer>?=null

    fun setCategoryAdapter(customerList: ArrayList<Customer>) {
        this.customerList=customerList
    }

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putInt("customerID", position + 1)
        val fragment = CustomerItemFragment()
        fragment.arguments = bundle
        return fragment
    }
}