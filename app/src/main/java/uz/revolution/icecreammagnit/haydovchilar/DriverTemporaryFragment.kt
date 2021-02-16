package uz.revolution.icecreammagnit.haydovchilar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_driver_temporary.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.database.AppDatabase
import uz.revolution.icecreammagnit.haydovchilar.adapters.DrTemporaryAdapter
import uz.revolution.icecreammagnit.haydovchilar.dialogs.DrTemporaryEditDialog
import uz.revolution.icecreammagnit.haydovchilar.dialogs.DriverCompleteDialog
import uz.revolution.icecreammagnit.models.Driver
import uz.revolution.icecreammagnit.models.Temporary
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val ARG_PARAM1 = "param1"

class DriverTemporaryFragment : Fragment() {
    private var param1: Int? = null

    lateinit var root: View
    val database = AppDatabase.get.getDatabase()
    val getMagnitDao = database.getProductDao()
    var myList = ArrayList<Temporary>()
    var drTemAdapter = DrTemporaryAdapter()
    var karobka_soni = 0

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
        root = inflater.inflate(R.layout.fragment_driver_temporary, container, false)

        loadList()
        loadAdapter()

        root.driver_temporary_float_action_btn.setOnClickListener {
            var bundle = Bundle()
            bundle.putInt("param1", param1!!)
            findNavController().navigate(R.id.driverChooseProductFragment, bundle)
        }

        root.driver_temporary_yakunlash_btn.setText("Yakunlash: $karobka_soni karobka")
        completeClick()
        onEditClick()

        return root
    }

    private fun loadList() {
        myList.clear()
        karobka_soni = 0
        var allList: ArrayList<Temporary> =
            getMagnitDao?.getAllTemporary()!! as ArrayList<Temporary>

        for (i in 0 until allList.size) {
            if (allList[i].driverID == param1) {
                myList.add(allList[i])
                karobka_soni += allList[i].numberReceived
            }
        }
    }


    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title =
            "Haydovchi $param1 | ${getCurrentDate()}"
    }

    private fun getCurrentDate(): String {
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
        return simpleDateFormat.format(Date())
    }

    private fun loadAdapter() {
        drTemAdapter.setAdapter(myList)
        root.driver_tempopary_recycler_view.layoutManager = LinearLayoutManager(root.context)
        root.driver_tempopary_recycler_view.adapter = drTemAdapter
        drTemAdapter.notifyDataSetChanged()
    }

    private fun completeClick() {

        root.driver_temporary_yakunlash_btn.setOnClickListener {
            if (myList.size > 0) {
                var berilganSumma = 0
                var tovar = ""
                var boxNumberCost = ""
                var totalBox = 0

                for (i in 0 until myList.size) {
                    berilganSumma += myList[i].numberReceived * myList[i].totalBox * myList[i].cost
                    boxNumberCost =
                        "${myList[i].numberReceived}x${myList[i].totalBox}x${myList[i].cost}"
                    tovar += "${myList[i].name}   ${boxNumberCost}\n"
                    totalBox += myList[i].numberReceived
                }

                val beginTansaction = childFragmentManager.beginTransaction()
                val dialog = DriverCompleteDialog.newInstance(berilganSumma, "Dialog")
                dialog.show(beginTansaction, "CompleteDialog")
                dialog.setOnPositiveClick(object : DriverCompleteDialog.OnPositiveClick {
                    override fun onClick(receivedCash: Int) {
                        getMagnitDao?.insertDriver(
                            Driver(
                                param1!!,
                                getCurrentDate(),
                                tovar,
                                totalBox,
                                berilganSumma,
                                receivedCash
                            )
                        )
                        findNavController().popBackStack()
                        Toast.makeText(root.context, "Yakunlandi!", Toast.LENGTH_SHORT).show()
                        getMagnitDao?.deleteTemporaryByDriverID(param1!!)
                        loadList()
                        loadAdapter()
                    }
                })
            } else {
                Toast.makeText(root.context, "Oldin mahsulot tanlang!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onEditClick() {
        drTemAdapter.setOnLongClick(object : DrTemporaryAdapter.OnLongClick {
            override fun onClick(temporary: Temporary) {
                val oldNumber = temporary.numberReceived
                val beginTransaction = childFragmentManager.beginTransaction()
                val dialog = DrTemporaryEditDialog.newInstance(
                    temporary,
                    getMagnitDao!!.getProductByName(temporary.name.toString()).balance
                )
                dialog.show(beginTransaction, "editDialog")
                dialog.setOnPositiveClick(object : DrTemporaryEditDialog.OnPositiveClick {
                    override fun onClick(temporary: Temporary) {
                        getMagnitDao.insertTemporary(
                            temporary
                        )
                        val deltaBox = oldNumber - temporary.numberReceived
                        getMagnitDao.addBalance(
                            deltaBox,
                            getMagnitDao.getProductByName(temporary.name.toString()).id
                        )
                        loadList()
                        loadAdapter()
                    }
                })
                dialog.setOnDeleteClick(object : DrTemporaryEditDialog.OnDeleteClick {
                    override fun onClick() {
                        getMagnitDao.addBalance(
                            oldNumber,
                            getMagnitDao.getProductByName(temporary.name.toString()).id
                        )
                        getMagnitDao.deleteTemporary(temporary)
                        drTemAdapter.notifyDataSetChanged()
                        Toast.makeText(root.context, "O'chirildi!", Toast.LENGTH_SHORT).show()
                        loadList()
                        loadAdapter()
                    }
                })
            }
        })
    }
}