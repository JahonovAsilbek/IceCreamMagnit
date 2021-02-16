package uz.revolution.icecreammagnit.haydovchilar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.driver_hisobot_bottom_dialog.view.*
import kotlinx.android.synthetic.main.fragment_driver_item.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.daos.MagnitDao
import uz.revolution.icecreammagnit.database.AppDatabase
import uz.revolution.icecreammagnit.haydovchilar.adapters.DriverItemAdapter
import uz.revolution.icecreammagnit.magnit.dialogs.MagnitCompleteDialog
import uz.revolution.icecreammagnit.models.Driver
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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
        addNewClick(bundle)


        root.driver_item_info.setOnClickListener {
            var berilgan_tovarlar = 0
            var olingan_summa = 0
            var jami_karobka=0
            for (i in 0 until myList!!.size) {
                berilgan_tovarlar += myList!![i].givenCash
                olingan_summa+=myList!![i].receivedCash
                jami_karobka += myList!![i].totalBox
            }
            var farq=berilgan_tovarlar-olingan_summa

            var berilganString = berilgan_tovarlar.toString().reversed()
            var olinganString = olingan_summa.toString().reversed()
            var farqString=farq.toString().reversed()
            if (berilganString.length >= 6) {
                berilganString = berilganString.substring(0, 6) + " " + berilganString.substring(6)
            }
            if (olinganString.length >= 6) {
            olinganString=olinganString.substring(0,6)+" "+olinganString.substring(6)
            }
            if (farqString.length >= 6) {
                farqString=farqString.substring(0,6)+" "+farqString.substring(6)
            }


            var myDialog = BottomSheetDialog(root.context,R.style.SheetDialog)
            var dialogView=LayoutInflater.from(root.context).inflate(R.layout.driver_hisobot_bottom_dialog,null,false)
            dialogView.date.text="Haydovchi ${param1!!+1} | Umumiy hisobot"
            dialogView.given_cash.text = "Berilgan tovarlar: ${berilganString.reversed()}"
            dialogView.received_cash.text="Olingan summa: ${olinganString.reversed()}"
            dialogView.total_box.text="Jami karobka: $jami_karobka"
            dialogView.farq.text="Farq: ${farqString.reversed()}"
            myDialog.setContentView(dialogView)
            myDialog.show()
        }
        return root
    }

    private fun addNewClick(bundle:Bundle) {
        var addedToday=false
        for (i in 0 until myList!!.size) {
            addedToday=myList!![i].date.equals(getCurrentDate(),true)
            if (addedToday) {
                break
            }
        }
        root.driver_item_yagi_qoshish_btn.setOnClickListener {
            if (addedToday) {
                val beginTransaction = childFragmentManager.beginTransaction()
                val dialog =
                    MagnitCompleteDialog.newInstance("Bugun uchun hisobot mavjud. Baribir qo'shilsinmi?")
                dialog.show(beginTransaction, "complete")
                dialog.setOnClick(object : MagnitCompleteDialog.OnClick {
                    override fun onClick() {
                        findNavController().navigate(R.id.driverTemporaryFragment, bundle)
                    }
                })
            } else {
                findNavController().navigate(R.id.driverTemporaryFragment, bundle)
            }
        }
    }

    private fun getCurrentDate(): String {
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
        return simpleDateFormat.format(Date())
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