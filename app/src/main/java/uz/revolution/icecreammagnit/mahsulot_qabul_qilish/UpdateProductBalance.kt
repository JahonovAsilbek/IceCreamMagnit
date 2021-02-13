package uz.revolution.icecreammagnit.mahsulot_qabul_qilish

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.item_set_cost_product.view.*
import kotlinx.android.synthetic.main.update_balance.view.*
import kotlinx.android.synthetic.main.update_balance_dialog.view.*
import kotlinx.android.synthetic.main.update_balance_dialog.view.update_balance_btn
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.daos.MagnitDao
import uz.revolution.icecreammagnit.database.AppDatabase
import uz.revolution.icecreammagnit.mahsulot_qabul_qilish.adapters.UpdateProductBalanceAdapter
import uz.revolution.icecreammagnit.mahsulot_qabul_qilish.dialogs.UpdateBalanceDialog
import uz.revolution.icecreammagnit.models.Product
import uz.revolution.icecreammagnit.models.ReceivedProducts

private const val ARG_PARAM1 = "supplierID"

class UpdateProductBalanceFragment : Fragment() {

    private var supplierID: Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            supplierID = it.getInt(ARG_PARAM1)
        }
    }

    lateinit var root: View
    var database = AppDatabase.get.getDatabase()
    var productDao: MagnitDao? = null
    lateinit var productList: ArrayList<Product>
    lateinit var adapter: UpdateProductBalanceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.update_balance, container, false)
//        (activity?.actionBar as AppCompatActivity).title=
        productDao = database.getProductDao()
        loadData()
        setActionBarTitle()
        adapter = UpdateProductBalanceAdapter(productList)
        root.update_balance_rv.adapter = adapter

        adapter.setOnItemClick(object : UpdateProductBalanceAdapter.OnItemClick {
            override fun onClick(product: Product, position: Int) {

                val beginTransaction = childFragmentManager.beginTransaction()
//                val dialog = UpdateBalanceDialog()
//                dialog.show(beginTransaction, "Dialog")
//                dialog.setOnAddClick(object : UpdateBalanceDialog.OnAddClick {
//                    override fun onClick(balance: Int) {
//                        productDao?.addBalance(balance, product.id)
//                    }
//                })
            }
        })

        root.update_balance_btn_all.setOnClickListener {
            var chindan_kelgani=false
            for (i in 0 until root.update_balance_rv.childCount) {
                var son: Int
                if (root.update_balance_rv.getChildAt(i).update_balance_edit.text.toString() != "") {
                    son =
                        Integer.parseInt(root.update_balance_rv.getChildAt(i).update_balance_edit.text.toString())
                } else {
                    son = 0
                }
                if (son != 0) {
                    chindan_kelgani = true
                    break
                }
            }
            if (chindan_kelgani) {
                for (i in 0 until root.update_balance_rv.childCount) {
                    var son: Int
                    if (root.update_balance_rv.getChildAt(i).update_balance_edit.text.toString() != "") {
                        son =
                            Integer.parseInt(root.update_balance_rv.getChildAt(i).update_balance_edit.text.toString())
                    } else {
                        son = 0
                    }
                    if (son != 0) {
                        chindan_kelgani = true
                    }
                    productDao?.addBalance(
                        son,
                        Integer.parseInt(root.update_balance_rv.getChildAt(i).product_name.tag.toString())
                    )
                }
                findNavController().popBackStack()
                Snackbar.make(it, "Muvaffaqiyatli qo'shildi", Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(it, "Barcha maydonlarni to'ldiring!", Snackbar.LENGTH_LONG).show()
            }
        }


        return root
    }

    private fun setActionBarTitle() {
        val supplier = productDao?.getSupplierByID(supplierID)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = supplier?.name
    }

    private fun loadData() {
        productList = ArrayList()
        productList = productDao?.getProductBySupplierID(supplierID) as ArrayList
    }

    companion object {
        fun newInstance(supplierID: Int) =
            UpdateProductBalanceFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, supplierID)
                }
            }
    }
}