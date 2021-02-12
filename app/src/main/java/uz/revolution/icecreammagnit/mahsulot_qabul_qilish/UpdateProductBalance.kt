package uz.revolution.icecreammagnit.mahsulot_qabul_qilish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.update_balance.view.*
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
                val dialog = UpdateBalanceDialog()
                dialog.show(beginTransaction, "Dialog")
                dialog.setOnAddClick(object : UpdateBalanceDialog.OnAddClick {
                    override fun onClick(balance: Int) {
                        productDao?.addBalance(balance, product.id)
                    }
                })
            }
        })

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