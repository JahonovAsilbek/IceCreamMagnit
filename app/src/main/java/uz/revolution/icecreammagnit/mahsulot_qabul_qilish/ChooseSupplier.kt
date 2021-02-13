package uz.revolution.icecreammagnit.mahsulot_qabul_qilish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.choose_supplier.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.daos.MagnitDao
import uz.revolution.icecreammagnit.database.AppDatabase
import uz.revolution.icecreammagnit.mahsulot_qabul_qilish.adapters.ChooseSupllierAdapter
import uz.revolution.icecreammagnit.models.Supplier

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ChooseSupplier : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var root: View
    private var database = AppDatabase.get.getDatabase()
    private var productDao: MagnitDao? = null
    lateinit var supplierList: ArrayList<Supplier>
    lateinit var adapter: ChooseSupllierAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.choose_supplier, container, false)

        productDao = database.getProductDao()
        loadData()
        adapter = ChooseSupllierAdapter(supplierList)
        root.choose_supplier_rv.adapter = adapter

        adapter.setOnItemClick(object : ChooseSupllierAdapter.OnItemClick {
            override fun onClick(id: Int) {
                val bundle = Bundle()
                bundle.putInt("supplierID", id)
                findNavController().navigate(R.id.setCostToSupplierProduct, bundle)
            }

        })


        return root
    }

    private fun loadData() {
        supplierList = ArrayList()
        supplierList = productDao?.getAllSuppliers() as ArrayList

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChooseSupplier().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}