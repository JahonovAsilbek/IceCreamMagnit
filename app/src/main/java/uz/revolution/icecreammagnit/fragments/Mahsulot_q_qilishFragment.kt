package uz.revolution.icecreammagnit.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_mahsulot_q_qilish.view.*
import kotlinx.android.synthetic.main.fragment_mahsulotlar.view.*
import kotlinx.android.synthetic.main.tab_item.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.adapters.ReceivedProductAdapter
import uz.revolution.icecreammagnit.daos.MagnitDao
import uz.revolution.icecreammagnit.database.AppDatabase
import uz.revolution.icecreammagnit.models.Product
import uz.revolution.icecreammagnit.models.ReceivedProducts
import uz.revolution.icecreammagnit.models.Supplier
import java.io.Serializable

private const val ARG_PARAM1 = "param1"

class Mahsulot_q_qilishFragment : Fragment() {

    private var param1: String? = null
//    private var productDao:MagnitDao?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
//        val database = AppDatabase.get.getDatabase()
//        productDao=database.getProductDao()
    }

    lateinit var root: View
    lateinit var adapter:ReceivedProductAdapter
    private var receivedList:ArrayList<ReceivedProducts>?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_mahsulot_q_qilish, container, false)

        loadData()
        adapter= ReceivedProductAdapter(receivedList!!,childFragmentManager)
        root.received_vp.adapter=adapter
        root.received_tab_layout.setupWithViewPager(root.received_vp)

        setTabs()

        root.received_tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            @SuppressLint("ResourceAsColor")
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val customView = tab?.customView
                customView?.circle_layout?.visibility = View.VISIBLE
                customView?.title_tv?.setTextColor(resources.getColor(R.color.white))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val customView = tab?.customView
                customView?.circle_layout?.visibility = View.INVISIBLE
                customView?.title_tv?.setTextColor(resources.getColor(R.color.black))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        return root
    }

    private fun setTabs() {
        val tabCount = root.received_tab_layout.tabCount

        for (i in 0 until tabCount) {
            val tabView = LayoutInflater.from(root.context).inflate(R.layout.tab_item, null, false)
            val tab = root.received_tab_layout.getTabAt(i)
            tab?.customView = tabView
            tabView.title_tv.text = receivedList!![i].supplierID.toString()

            if (i == 0) {
                tabView.circle_layout.visibility = View.VISIBLE
                tabView?.title_tv?.setTextColor(resources.getColor(R.color.white))
            } else {
                tabView.circle_layout.visibility = View.INVISIBLE
                tabView?.title_tv?.setTextColor(resources.getColor(R.color.black))
            }
        }
    }

    private fun loadData() {
        receivedList=ArrayList<ReceivedProducts>()
//        receivedList=productDao?.getAllReceivedProducts() as ArrayList

        receivedList?.add(ReceivedProducts(0,0,"11.02.2021","Maro'jni",25,2500000,2000000))
        receivedList?.add(ReceivedProducts(1,1,"11.02.2021","Maro'jni",25,2500000,2000000))
        receivedList?.add(ReceivedProducts(2,2,"11.02.2021","Maro'jni",25,2500000,2000000))
        receivedList?.add(ReceivedProducts(3,3,"11.02.2021","Maro'jni",25,2500000,2000000))
        receivedList?.add(ReceivedProducts(4,4,"11.02.2021","Maro'jni",25,2500000,2000000))
        receivedList?.add(ReceivedProducts(5,5,"11.02.2021","Maro'jni",25,2500000,2000000))

    }

    companion object {

        fun newInstance(param1: String) =
            Mahsulot_q_qilishFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

}