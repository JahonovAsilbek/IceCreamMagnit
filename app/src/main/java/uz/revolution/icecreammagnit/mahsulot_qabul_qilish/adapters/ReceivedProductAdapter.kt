package uz.revolution.icecreammagnit.mahsulot_qabul_qilish.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import uz.revolution.icecreammagnit.mahsulot_qabul_qilish.Mahsulot_q_qilishFragment
import uz.revolution.icecreammagnit.mahsulot_qabul_qilish.ReceivedProductFragment
import uz.revolution.icecreammagnit.models.ReceivedProducts

class ReceivedProductAdapter(
    var receivedProductsBySupplierID: ArrayList<Mahsulot_q_qilishFragment.Category>,
    fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = receivedProductsBySupplierID.size

    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putSerializable("received", receivedProductsBySupplierID[position].receivedListBySupplierID)
        val fragment = ReceivedProductFragment()
        fragment.arguments = bundle
        return fragment
    }
}