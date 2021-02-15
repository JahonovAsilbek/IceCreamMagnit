package uz.revolution.icecreammagnit.mijozlar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.choose_product_customer.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.daos.MagnitDao
import uz.revolution.icecreammagnit.database.AppDatabase
import uz.revolution.icecreammagnit.mijozlar.adapters.ChooseProductCustomerAdapter
import uz.revolution.icecreammagnit.mijozlar.dialogs.CustomerSetBoxDialog
import uz.revolution.icecreammagnit.models.CustomerTemporary
import uz.revolution.icecreammagnit.models.Product

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "customer_id"
private const val ARG_PARAM2 = "param2"

class ChooseProductCustomer : Fragment() {

    private var param1: Int = 0
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        Log.d("AAAA", "ChooseProductCustomer id: $param1")
        database = AppDatabase.get.getDatabase()
        magnitDao = database!!.getProductDao()
        adapter = ChooseProductCustomerAdapter("mijoz")
    }

    lateinit var root: View
    private var productList: ArrayList<Product>? = null
    private var database: AppDatabase? = null
    private var magnitDao: MagnitDao? = null
    lateinit var adapter: ChooseProductCustomerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.choose_product_customer, container, false)

        loadData()
        loadAdapters()
        dialogClick()

        return root
    }

    private fun dialogClick() {
        adapter.setOnItemClick(object : ChooseProductCustomerAdapter.OnItemCLick {
            override fun onClick(product: Product, position: Int) {
                if (product.balance > 0) {
                    val beginTansaction = childFragmentManager.beginTransaction()
                    val dialog = CustomerSetBoxDialog.newInstance(product, "dialog")
                    dialog.show(beginTansaction, "Dialog")
                    dialog.setOnPositiveClick(object : CustomerSetBoxDialog.OnPostiveClick {
                        override fun onClick(box: Int) {
                            val mahsulotSummasi: Int = box * product.costCustomer * product.totalBox
                            magnitDao?.insertCustomerTemporary(
                                CustomerTemporary(
                                    param1,
                                    product.name!!,
                                    mahsulotSummasi,
                                    product.costCustomer,
                                    box,
                                    product.totalBox
                                )
                            )
                            magnitDao?.subtractBalance(box, product.id)

                            val snackBar =
                                Snackbar.make(
                                    root,
                                    "Muvaffaqiyatli qo'shildi",
                                    Snackbar.LENGTH_LONG
                                )
                            val sView = snackBar.view
                            sView.background = resources.getDrawable(R.drawable.btn_back)
                            snackBar.show()

                            loadData()
                            loadAdapters()
                            adapter.notifyDataSetChanged()
                            findNavController().popBackStack()
                        }
                    })
                } else {
                    Toast.makeText(root.context, "Bazada mahsulot yo'q", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun loadAdapters() {
        adapter.setAdapter(productList!!)
        root.choose_product_rv.adapter = adapter
    }

    private fun loadData() {
        if (productList != null) {
            productList?.clear()
        }
        productList = ArrayList()
        productList = magnitDao?.getProductList() as ArrayList
    }

}