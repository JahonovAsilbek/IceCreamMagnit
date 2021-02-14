package uz.revolution.icecreammagnit.haydovchilar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_driver_item.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.database.AppDatabase
import uz.revolution.icecreammagnit.haydovchilar.adapters.DriverItemAdapter
import uz.revolution.icecreammagnit.models.Driver
import java.io.Serializable

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DriverItemFragment : Fragment() {
    private var param1: Int? = null
    lateinit var root:View

    val database = AppDatabase.get.getDatabase()
    val getMagnitDao = database.getProductDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        Log.d("TUIT", "onCreateView: ${param1!![0].date}")
        val allList=getMagnitDao?.getAllDriver()!!
        var myList = ArrayList<Driver>()
        for (i in 0 until allList.size) {
            if (allList[i].serialNumber == param1!! + 1) {
                myList.add(allList[i])
            }
        }
        root = inflater.inflate(R.layout.fragment_driver_item, container, false)
        var driverItemAdapter = DriverItemAdapter(myList)
        root.driver_item_recyclerview.layoutManager=LinearLayoutManager(this.context)
        root.driver_item_recyclerview.adapter=driverItemAdapter
        return root
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