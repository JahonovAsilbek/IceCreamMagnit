package uz.revolution.icecreammagnit.mahsulot_qabul_qilish

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.mahsulot_q_qilish.view.*
import kotlinx.android.synthetic.main.tab_item.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.daos.MagnitDao
import uz.revolution.icecreammagnit.database.AppDatabase
import uz.revolution.icecreammagnit.mahsulot_qabul_qilish.adapters.ReceivedProductAdapter
import uz.revolution.icecreammagnit.models.ReceivedProducts

private const val ARG_PARAM1 = "param1"

class Mahsulot_q_qilishFragment : Fragment() {

    private var param1: String? = null
    private var productDao: MagnitDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
        setHasOptionsMenu(true)
        database = AppDatabase.get.getDatabase()
        productDao = database!!.getProductDao()
        adapter = ReceivedProductAdapter(childFragmentManager)
    }

    lateinit var root: View
    lateinit var adapter: ReceivedProductAdapter
    private lateinit var receivedListBySupplierID: ArrayList<ReceivedProducts>
    var database: AppDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.mahsulot_q_qilish, container, false)

        loadData()
        loadAdapters()
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

    private fun loadAdapters() {
        adapter.setAdapter(receivedListBySupplierID)
        root.received_vp.adapter = adapter
        root.received_tab_layout.setupWithViewPager(root.received_vp)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.received_products_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.show_suppliers) {
            findNavController().navigate(R.id.chooseSupplier)
        }
        return true
    }

    @SuppressLint("SetTextI18n")
    private fun setTabs() {
        val tabCount = root.received_tab_layout.tabCount

        for (i in 0 until tabCount) {
            val tabView = LayoutInflater.from(root.context).inflate(R.layout.tab_item, null, false)
            val tab = root.received_tab_layout.getTabAt(i)
            tab?.customView = tabView
            when (i) {
                0 -> tabView.title_tv.text = "Royal Muz Servis"
                1 -> tabView.title_tv.text = "Vazira MCHJ"
                2 -> tabView.title_tv.text = "Imkon Plus"
                3 -> tabView.title_tv.text = "Ice & Gold(Bekobod)"
                4 -> tabView.title_tv.text = "Dairy Classic(Singapur)"
                5 -> tabView.title_tv.text = "Ulug'bek aka(Buxoro)"
                6 -> tabView.title_tv.text = "Boshqalar..."
            }

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
        receivedListBySupplierID = ArrayList()
        receivedListBySupplierID.addAll((productDao!!.getReceivedProductsBySupplierID(1) as ArrayList))
        receivedListBySupplierID.addAll((productDao!!.getReceivedProductsBySupplierID(2) as ArrayList))
        receivedListBySupplierID.addAll((productDao!!.getReceivedProductsBySupplierID(3) as ArrayList))
        receivedListBySupplierID.addAll((productDao!!.getReceivedProductsBySupplierID(4) as ArrayList))
        receivedListBySupplierID.addAll((productDao!!.getReceivedProductsBySupplierID(5) as ArrayList))
        receivedListBySupplierID.addAll((productDao!!.getReceivedProductsBySupplierID(6) as ArrayList))
        receivedListBySupplierID.addAll((productDao!!.getReceivedProductsBySupplierID(7) as ArrayList))

    }
}