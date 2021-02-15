package uz.revolution.icecreammagnit.mijozlar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_customer.view.*
import kotlinx.android.synthetic.main.item_bottomsheet.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.daos.MagnitDao
import uz.revolution.icecreammagnit.database.AppDatabase
import uz.revolution.icecreammagnit.mijozlar.adapters.CustomerAdapter
import uz.revolution.icecreammagnit.models.Customer

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
        database=AppDatabase.get.getDatabase()
        magnitDao=database!!.getProductDao()
        adapter = CustomerAdapter()
        customerList= ArrayList()
    }

    lateinit var root: View
    lateinit var adapter: CustomerAdapter
    private var customerList:ArrayList<Customer>?=null
    private var database:AppDatabase?=null
    private var magnitDao: MagnitDao?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_customer, container, false)

        loadData()
        loadAdapter()

        root.set_new.setOnClickListener {
            val bundle = Bundle()
            Log.d("AAAA", "kegan customer id: $param2")
            bundle.putInt("id", param2)
            when (param2) {
                1 -> bundle.putString("mijoz", "Bahrom aka(Shofirjon)")
                2 -> bundle.putString("mijoz", "Boshqalar")
            }
            findNavController().navigate(R.id.customerTemporaryFragment, bundle)
        }

        adapter.setOnItemClick(object : CustomerAdapter.OnItemClick {
            override fun onClick(customer: Customer) {
                val dialog = BottomSheetDialog(root.context, R.style.SheetDialog)

                val view = layoutInflater.inflate(R.layout.item_bottomsheet, null, false)
                view.date.text = "Sana:  ${customer.date}"
                view.given_cash.text = "Mijozdan olingan summa:  ${customer.receivedCash}"
                view.received_cash.text = "Mahsulotlar summasi:  ${customer.givenCash}"
                view.products.text = "Tovarlar: \n${customer.product}"
                view.total_box.text = "Ja'mi karobka:  ${customer.totalBox.toString()}"

                view.share.setOnClickListener {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            view.date.text.toString()
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

        return root
    }

    private fun loadData() {
        customerList?.clear()
        customerList=magnitDao?.getCustomerBySerialNumber(param2) as ArrayList
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