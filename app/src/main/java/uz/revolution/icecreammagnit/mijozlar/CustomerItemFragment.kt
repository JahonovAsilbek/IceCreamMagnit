package uz.revolution.icecreammagnit.mijozlar

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.driver_hisobot_bottom_dialog.view.*
import kotlinx.android.synthetic.main.fragment_customer.view.*
import kotlinx.android.synthetic.main.item_bottomsheet.view.*
import kotlinx.android.synthetic.main.item_bottomsheet.view.date
import kotlinx.android.synthetic.main.item_bottomsheet.view.given_cash
import kotlinx.android.synthetic.main.item_bottomsheet.view.products
import kotlinx.android.synthetic.main.item_bottomsheet.view.received_cash
import kotlinx.android.synthetic.main.item_bottomsheet.view.share
import kotlinx.android.synthetic.main.item_bottomsheet.view.total_box
import kotlinx.android.synthetic.main.item_bottomsheet_customer.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.daos.MagnitDao
import uz.revolution.icecreammagnit.database.AppDatabase
import uz.revolution.icecreammagnit.magnit.dialogs.MagnitCompleteDialog
import uz.revolution.icecreammagnit.mijozlar.adapters.CustomerAdapter
import uz.revolution.icecreammagnit.models.Customer
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "customerID"

class CustomerItemFragment : Fragment() {

    private var param1: ArrayList<Customer>? = null
    private var param2: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param2 = it.getInt(ARG_PARAM2)
        }
        val bundle = arguments
        param2 = bundle?.getInt("customerID")!!
        database = AppDatabase.get.getDatabase()
        magnitDao = database!!.getProductDao()
        adapter = CustomerAdapter()
        customerList = ArrayList()
    }

    lateinit var root: View
    lateinit var adapter: CustomerAdapter
    private var customerList: ArrayList<Customer>? = null
    private var database: AppDatabase? = null
    private var magnitDao: MagnitDao? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_customer, container, false)

        loadData()
        loadAdapter()
        infoClick()
        itemClick()
        addNewClick()

        return root
    }

    private fun addNewClick() {
        var addedToday=false
        for (i in 0 until customerList!!.size) {
            addedToday = customerList!![i].date.equals(getCurrentDate(), true)
            if (addedToday) {
                break
            }
        }

        root.set_new.setOnClickListener {
            if (addedToday) {
                val beginTransaction = childFragmentManager.beginTransaction()
                val dialog =
                    MagnitCompleteDialog.newInstance("Bugun uchun hisobot mavjud. Baribir qo'shilsinmi?")
                dialog.show(beginTransaction, "complete")
                dialog.setOnClick(object : MagnitCompleteDialog.OnClick {
                    override fun onClick() {
                        val bundle = Bundle()
                        Log.d("AAAA", "kegan customer id: $param2")
                        bundle.putInt("id", param2)
                        when (param2) {
                            1 -> bundle.putString("mijoz", "Bahrom aka(Shofirjon)")
                            2 -> bundle.putString("mijoz", "Boshqalar")
                        }
                        findNavController().navigate(R.id.customerTemporaryFragment, bundle)
                    }
                })
            } else {
                val bundle = Bundle()
                Log.d("AAAA", "kegan customer id: $param2")
                bundle.putInt("id", param2)
                when (param2) {
                    1 -> bundle.putString("mijoz", "Bahrom aka(Shofirjon)")
                    2 -> bundle.putString("mijoz", "Boshqalar")
                }
                findNavController().navigate(R.id.customerTemporaryFragment, bundle)
            }
        }
    }

    private fun getCurrentDate(): String {
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
        return simpleDateFormat.format(Date())
    }

    private fun itemClick() {
        adapter.setOnItemClick(object : CustomerAdapter.OnItemClick {
            override fun onClick(customer: Customer) {
                val dialog = BottomSheetDialog(root.context, R.style.SheetDialog)

                val view = layoutInflater.inflate(R.layout.item_bottomsheet_customer, null, false)
                var mijoz = ""
                if (param2 == 1) {
                    mijoz = "Bahrom aka"
                } else {
                    mijoz = "Boshqalar"
                }
                view.mijoz.text = mijoz
                view.date.text = "${customer.date}"
                view.given_cash.text = "Berilgan tovarlar:  ${customer.givenCash}"
                view.received_cash.text = "Olingan summa:  ${customer.receivedCash}"
                view.products.text = "Tovarlar: \n${customer.product}"
                view.total_box.text = "Ja'mi karobka:  ${customer.totalBox}"

                view.share.setOnClickListener {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            mijoz + " | " + view.date.text.toString()
                                    + "\n" +
                                    view.given_cash.text.toString()
                                    + "\n" +
                                    view.received_cash.text.toString()
                                    + "\n" +
                                    view.products.text.toString()
                                    + "\n\n" +
                                    view.total_box.text.toString()
                        )
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)
                }
                dialog.setContentView(view)
                dialog.show()
            }

        })
    }


    @SuppressLint("SetTextI18n")
    private fun infoClick() {
        var mahsulotlarSummasi = 0
        var mijozBerganSumma = 0
        var totalBox = 0

        for (i in 0 until customerList!!.size) {
            mahsulotlarSummasi += customerList!![i].givenCash
            mijozBerganSumma += customerList!![i].receivedCash
            totalBox += customerList!![i].totalBox
        }
        val deltaCash = mahsulotlarSummasi - mijozBerganSumma

        root.customer_info.setOnClickListener {
            if (customerList != null) {
                val dialog = BottomSheetDialog(root.context, R.style.SheetDialog)
                val view = LayoutInflater.from(root.context)
                    .inflate(R.layout.driver_hisobot_bottom_dialog, null, false)
                if (param2 == 1) {
                    view.date.text = "Bahrom aka(Shofirjon) | Umumiy hisobot"
                } else {
                    view.date.text = "Boshqalar | Umumiy hisobot"
                }
                view.given_cash.text = "Berilgan tovarlar: ${mahsulotlarSummasi}"
                view.received_cash.text = "Olingan summa: ${mijozBerganSumma}"
                view.total_box.text = "Jami karobka: $totalBox"
                view.farq.text = "Farq: $deltaCash"
                dialog.setContentView(view)
                dialog.show()
            } else {
                Toast.makeText(root.context, "Ma'lumotlar mavjud emas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadData() {
        customerList?.clear()
        customerList = magnitDao?.getCustomerBySerialNumber(param2) as ArrayList
    }

    private fun loadAdapter() {
        customerList!!.reverse()
        adapter.setAdapter(customerList!!)
        root.rv.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        loadData()
        loadAdapter()
    }

    companion object {

        @JvmStatic
        fun newInstance(param2: Int) =
            CustomerItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM2, param2)
                }
            }
    }

}