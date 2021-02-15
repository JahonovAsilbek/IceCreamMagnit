package uz.revolution.icecreammagnit.haydovchilar

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.haydovchi.view.*
import kotlinx.android.synthetic.main.tab_item.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.database.AppDatabase
import uz.revolution.icecreammagnit.haydovchilar.adapters.DriverViewPagerAdapter
import uz.revolution.icecreammagnit.models.Driver
import uz.revolution.icecreammagnit.models.Temporary

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HaydovchiFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    val database = AppDatabase.get.getDatabase()
    val getMagnitDao = database.getProductDao()
    lateinit var root: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.haydovchi, container, false)
        var viewPagerAdapter =
            DriverViewPagerAdapter(getMagnitDao?.getAllDriver()!!, childFragmentManager)
        root.driver_view_pager.adapter = viewPagerAdapter

//        loadData()
        root.driver_tab_layout.setupWithViewPager(root.driver_view_pager)
        setTabs()
        root.driver_tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HaydovchiFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HaydovchiFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun setTabs() {
        val tabCount = root.driver_tab_layout.tabCount

        for (i in 0 until tabCount) {
            val tabView = LayoutInflater.from(root.context).inflate(R.layout.tab_item, null, false)
            val tab = root.driver_tab_layout.getTabAt(i)
            tab?.customView = tabView
            if (i == 0) {
                tabView.title_tv.text = "Haydovchi 1"
            } else tabView.title_tv.text = "Haydovchi 2"

            if (i == 0) {
                tabView.circle_layout.visibility = View.VISIBLE
                tabView?.title_tv?.setTextColor(resources.getColor(R.color.white))
            } else {
                tabView.circle_layout.visibility = View.INVISIBLE
                tabView?.title_tv?.setTextColor(resources.getColor(R.color.black))
            }
        }
    }

}