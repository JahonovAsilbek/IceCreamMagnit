package uz.revolution.icecreammagnit.haydovchilar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_driver_item.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.daos.MagnitDao
import uz.revolution.icecreammagnit.database.AppDatabase
import uz.revolution.icecreammagnit.haydovchilar.adapters.DriverItemAdapter
import uz.revolution.icecreammagnit.models.Driver
import java.io.Serializable

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DriverItemFragment : Fragment() {
    private var param1: Int? = null
    lateinit var root:View

    private var database : AppDatabase?=null
    private var getMagnitDao:MagnitDao?=null

    private var myList:ArrayList<Driver>?=null
    lateinit var driverItemAdapter:DriverItemAdapter
    private var allList:ArrayList<Driver>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
        database = AppDatabase.get.getDatabase()
        getMagnitDao = database!!.getProductDao()
        driverItemAdapter=DriverItemAdapter()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loadData()
        root = inflater.inflate(R.layout.fragment_driver_item, container, false)

        val bundle = Bundle()
        bundle.putInt("param1",(param1!!+1))

        root.driver_item_yagi_qoshish_btn.setOnClickListener {
            findNavController().navigate(R.id.driverTemporaryFragment,bundle)
        }
        return root
    }

    private fun loadData() {
        allList= ArrayList()
        allList=getMagnitDao?.getAllDriver() as ArrayList
        myList= ArrayList()
        for (i in 0 until allList!!.size) {
            if (allList!![i].serialNumber == (param1!! + 1)) {
                myList!!.add(allList!![i])
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
        loadAdapter()
    }

    private fun loadAdapter() {
        myList!!.reverse()
        driverItemAdapter.setAdapter(myList!!)
        root.driver_item_recyclerview.layoutManager=LinearLayoutManager(this.context)
        root.driver_item_recyclerview.adapter=driverItemAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            DriverItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }

}