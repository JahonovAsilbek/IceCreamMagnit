package uz.revolution.icecreammagnit.mahsulot_qabul_qilish.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import uz.revolution.icecreammagnit.mahsulot_qabul_qilish.ReceivedProductFragment
import uz.revolution.icecreammagnit.models.ReceivedProducts

class ReceivedProductAdapter(
    var receivedList: ArrayList<ReceivedProducts>,
    fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = receivedList.size

    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putSerializable("received", receivedList)
        val fragment = ReceivedProductFragment()
        fragment.arguments = bundle
        return fragment
    }
}