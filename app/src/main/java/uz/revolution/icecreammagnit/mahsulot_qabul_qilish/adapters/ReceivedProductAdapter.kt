package uz.revolution.icecreammagnit.mahsulot_qabul_qilish.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import uz.revolution.icecreammagnit.mahsulot_qabul_qilish.Mahsulot_q_qilishFragment
import uz.revolution.icecreammagnit.mahsulot_qabul_qilish.ReceivedProductFragment
import uz.revolution.icecreammagnit.models.ReceivedProducts

class ReceivedProductAdapter(fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var receivedProductsBySupplierID:ArrayList<ReceivedProducts>?=null

    fun setAdapter(receivedProductsBySupplierID: ArrayList<ReceivedProducts>) {
        this.receivedProductsBySupplierID=receivedProductsBySupplierID
    }

    override fun getCount(): Int = 7

    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putInt("supplierID", position+1)
        val fragment = ReceivedProductFragment()
        fragment.arguments = bundle
        return fragment
    }
}