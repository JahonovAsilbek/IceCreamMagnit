package uz.revolution.icecreammagnit.mijozlar

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.mahsulot_q_qilish.view.*
import kotlinx.android.synthetic.main.mijozlar.view.*
import kotlinx.android.synthetic.main.tab_item.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.daos.MagnitDao
import uz.revolution.icecreammagnit.database.AppDatabase
import uz.revolution.icecreammagnit.mijozlar.adapters.CategoryAdapter
import uz.revolution.icecreammagnit.models.Customer
import uz.revolution.icecreammagnit.models.CustomerTemporary

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class MijozlarFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        database=AppDatabase.get.getDatabase()
        magnitDao=database!!.getProductDao()
        adapter = CategoryAdapter(childFragmentManager)
    }

    lateinit var root:View
    lateinit var customerList: ArrayList<Category>
    lateinit var adapter:CategoryAdapter
    private var database:AppDatabase?=null
    private var magnitDao:MagnitDao?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.mijozlar, container, false)

        loadData()
        loadAdapter()
        setTabs()
        return root
    }

    private fun loadAdapter() {
        adapter.setCategoryAdapter(customerList)
        root.customer_vp.adapter = adapter
        root.customer_tab_layout.setupWithViewPager(root.customer_vp)
        adapter.notifyDataSetChanged()
    }

    private fun loadData() {
        customerList = ArrayList()
        customerList.add(Category("Bahrom aka(Shofirkon)",magnitDao!!.getCustomerBySerialNumber(1) as ArrayList))
        customerList.add(Category("Boshqalar",magnitDao!!.getCustomerBySerialNumber(2) as ArrayList))
    }

    inner class Category(var title:String,var arrayList:ArrayList<Customer>)

    private fun setTabs() {
        val tabCount = root.customer_tab_layout.tabCount

        for (i in 0 until tabCount) {
            val tabView = LayoutInflater.from(root.context).inflate(R.layout.tab_item, null, false)
            val tab = root.customer_tab_layout.getTabAt(i)
            tab?.customView = tabView
            tabView.title_tv.text = customerList[i].title

            if (i == 0) {
                tabView.circle_layout.visibility = View.VISIBLE
                tabView?.title_tv?.setTextColor(resources.getColor(R.color.white))
            } else {
                tabView.circle_layout.visibility = View.INVISIBLE
                tabView?.title_tv?.setTextColor(resources.getColor(R.color.black))
            }
        }

        root.customer_tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
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
    }
}