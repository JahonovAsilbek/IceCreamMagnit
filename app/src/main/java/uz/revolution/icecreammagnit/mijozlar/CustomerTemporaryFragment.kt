package uz.revolution.icecreammagnit.mijozlar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.customer_temporary.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.daos.MagnitDao
import uz.revolution.icecreammagnit.database.AppDatabase
import uz.revolution.icecreammagnit.mijozlar.adapters.CustomerTemporaryAdapter
import uz.revolution.icecreammagnit.mijozlar.dialogs.CustomerCompleteDialog
import uz.revolution.icecreammagnit.mijozlar.dialogs.CustomerEditDialog
import uz.revolution.icecreammagnit.models.Customer
import uz.revolution.icecreammagnit.models.CustomerTemporary
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val ARG_PARAM1 = "id"
private const val ARG_PARAM2 = "mijoz"

class CustomerTemporaryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Int = 0
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        Log.d("AAAA", "customer temporary: id=$param1 ")
        (activity as AppCompatActivity).supportActionBar?.title = param2
        database = AppDatabase.get.getDatabase()
        magnitDao = database!!.getProductDao()
        adapter = CustomerTemporaryAdapter()
        customerTemporaryList = ArrayList()
    }

    lateinit var root: View
    private var customerTemporaryList: ArrayList<CustomerTemporary>? = null
    private var database: AppDatabase? = null
    private var magnitDao: MagnitDao? = null
    lateinit var adapter: CustomerTemporaryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.customer_temporary, container, false)

        loadData()
        loadAdapter()
        clickFloatActionButton()
        completeClick()
        onEditClick()

        return root
    }

    private fun onEditClick() {
        adapter.setOnLongClick(object : CustomerTemporaryAdapter.OnLongClick {
            override fun onClick(customerTemporary: CustomerTemporary) {
                val data = customerTemporary
                val beginTransaction = childFragmentManager.beginTransaction()
                val dialog = CustomerEditDialog.newInstance(
                    customerTemporary,
                    magnitDao!!.getProductByName(customerTemporary.name.toString()).balance
                )
                dialog.show(beginTransaction, "editDialog")
                dialog.setOnPositiveClick(object : CustomerEditDialog.OnPositiveClick {
                    override fun onClick(customerTemporary: CustomerTemporary) {
                        magnitDao?.updateCustomerTemporaryByCustomerID(
                            customerTemporary.name.toString(),
                            customerTemporary.mahsulotlarSummasi,
                            customerTemporary.customerCost,
                            customerTemporary.box,
                            customerTemporary.karobkadaSoni,
                            data.id
                        )
                        val deltaBox = data.box - customerTemporary.box
                        magnitDao?.addBalance(
                            deltaBox,
                            magnitDao!!.getProductByName(customerTemporary.name.toString()).id
                        )
                        loadData()
                        loadAdapter()
                    }
                })
                dialog.setOnDeleteClick(object : CustomerEditDialog.OnDeleteClick {
                    override fun onClick() {
                        magnitDao?.addBalance(
                            data.box,
                            magnitDao!!.getProductByName(customerTemporary.name.toString()).id
                        )
                        magnitDao?.deleteCustomerTemporary(data)
                        adapter.notifyDataSetChanged()
                        Toast.makeText(root.context, "O'chirildi!", Toast.LENGTH_SHORT).show()
                        loadData()
                        loadAdapter()
                    }
                })
            }
        })
    }

    private fun completeClick() {
        if (customerTemporaryList!!.size > 0) {
            root.yakunlash.setOnClickListener {
                var berilganSumma = 0
                var tovar = ""
                var boxNumberCost = ""
                var totalBox = 0

                for (i in 0 until customerTemporaryList!!.size) {
                    berilganSumma += customerTemporaryList!![i].mahsulotlarSummasi
                    boxNumberCost =
                        "\n${customerTemporaryList!![i].box}x${customerTemporaryList!![i].karobkadaSoni}x${customerTemporaryList!![i].customerCost}"
                    tovar += "${customerTemporaryList!![i].name}   ${boxNumberCost}\n"
                    totalBox += customerTemporaryList!![i].box
                }

                val beginTansaction = childFragmentManager.beginTransaction()
                val dialog = CustomerCompleteDialog.newInstance(berilganSumma, "Dialog")
                dialog.show(beginTansaction, "CompleteDialog")
                dialog.setOnPositiveClick(object : CustomerCompleteDialog.OnPositiveClick {
                    override fun onClick(receivedCash: Int) {
                        magnitDao?.insertCustomer(
                            Customer(
                                param1,
                                getCurrentDate(),
                                tovar,
                                totalBox,
                                berilganSumma,
                                receivedCash
                            )
                        )
                        findNavController().popBackStack()
                        Toast.makeText(root.context, "Yakunlandi!", Toast.LENGTH_SHORT).show()
                        magnitDao?.deleteAllCustomerTemporary()
                        loadData()
                        loadAdapter()
                    }
                })
            }
        }
    }

    private fun clickFloatActionButton() {
        root.floatingActionButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("customer_id", param1)
            findNavController().navigate(R.id.chooseProductCustomer, bundle)
        }
    }

    private fun loadAdapter() {
        adapter.setAdapter(customerTemporaryList!!)
        root.customer_temporary_rv.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun loadData() {
        customerTemporaryList = magnitDao?.getCustomerTemporaryByCustomerID(param1) as ArrayList
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = param2
        adapter.notifyDataSetChanged()
    }

    private fun getCurrentDate(): String {
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
        return simpleDateFormat.format(Date())
    }

}