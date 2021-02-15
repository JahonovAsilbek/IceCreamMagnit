package uz.revolution.icecreammagnit.haydovchilar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.choose_product_customer.view.*
import kotlinx.android.synthetic.main.fragment_driver_choose_product.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.database.AppDatabase
import uz.revolution.icecreammagnit.mijozlar.adapters.ChooseProductCustomerAdapter
import uz.revolution.icecreammagnit.mijozlar.dialogs.CustomerSetBoxDialog
import uz.revolution.icecreammagnit.models.CustomerTemporary
import uz.revolution.icecreammagnit.models.Product
import uz.revolution.icecreammagnit.models.Temporary

private const val ARG_PARAM1 = "param1"

class DriverChooseProductFragment : Fragment() {

    private var param1: Int? = null

    lateinit var root:View
    var productList :ArrayList<Product>?=null
    val database = AppDatabase.get.getDatabase()
    val getMagnitDao = database.getProductDao()

    var drChoosePrAdapter=ChooseProductCustomerAdapter("driver")

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
        root = inflater.inflate(R.layout.fragment_driver_choose_product, container, false)
        loadData()
        loadAdapters()
        dialogClick()
        root.dr_choose_product_recycler_view.adapter = drChoosePrAdapter
        root.dr_choose_product_recycler_view.layoutManager = LinearLayoutManager(root.context)
        root.dr_choose_product_bekor_qilish_btn.setOnClickListener {
            findNavController().popBackStack()
        }

        return root
    }
    private fun loadData() {
        if (productList != null) {
            productList?.clear()
        }
        productList = ArrayList()
        productList = getMagnitDao?.getProductList() as ArrayList
    }
    private fun dialogClick() {
        drChoosePrAdapter.setOnItemClick(object : ChooseProductCustomerAdapter.OnItemCLick {
            override fun onClick(product: Product, position: Int) {
                if (product.balance > 0) {
                    val beginTansaction = childFragmentManager.beginTransaction()
                    val dialog = CustomerSetBoxDialog.newInstance(product, "dialog")
                    dialog.show(beginTansaction, "Dialog")
                    dialog.setOnPositiveClick(object : CustomerSetBoxDialog.OnPostiveClick {
                        override fun onClick(box: Int) {
                            getMagnitDao?.insertTemporary(
                                Temporary(
                                    product.name!!,
                                    param1!!,
                                    box,
                                    product.totalBox,
                                    product.costDriver
                                )
                            )
                            getMagnitDao?.subtractBalance(box, product.id)

                            val snackBar =
                                Snackbar.make(
                                    root,
                                    "Muvaffaqiyatli qo'shildi",
                                    Snackbar.LENGTH_LONG
                                )
                            val sView = snackBar.view
                            sView.background = resources.getDrawable(R.drawable.btn_back)
                            snackBar.show()
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
        drChoosePrAdapter.setAdapter(productList!!)
        root.dr_choose_product_recycler_view.adapter = drChoosePrAdapter
    }
}