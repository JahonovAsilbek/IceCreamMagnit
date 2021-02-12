package uz.revolution.icecreammagnit.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.delete_prdct_item_dialog.view.*
import kotlinx.android.synthetic.main.mahsulotlar.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.database.AppDatabase
import uz.revolution.icecreammagnit.mahsulotlar.adapters.ProductAdapter
import uz.revolution.icecreammagnit.models.Product
import uz.revolution.icecreammagnit.models.Supplier

class MahsulotlarFragment : Fragment() {

    lateinit var root: View
    lateinit var productAdapter: ProductAdapter
    lateinit var productList: List<Product>
    lateinit var suppliertList: List<Supplier>
    val database = AppDatabase.get.getDatabase()
    val getMagnitDao = database.getProductDao()

    fun loadData() {
        productList = ArrayList()

        productList = getMagnitDao?.getProductList()!!
        suppliertList=getMagnitDao.getAllSuppliers()

        productAdapter = ProductAdapter(productList, suppliertList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        loadData()
        root = inflater.inflate(R.layout.mahsulotlar, container, false)
        root.product_recycler_view.adapter = productAdapter
        root.product_recycler_view.layoutManager = LinearLayoutManager(container?.context)
        loadSwipeFun()

        return root
    }

    private fun loadSwipeFun() {
        val itemTouch = object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val swipeFlags = ItemTouchHelper.START

                return makeMovementFlags(0, swipeFlags)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val view = layoutInflater.inflate(R.layout.delete_prdct_item_dialog, null, false)
                val alertdialog = AlertDialog.Builder(root.context)
                val dialog = alertdialog.create()
                dialog.setView(view)

                view.delete_product_dialog_davom_etish_.setOnClickListener {
                    val pd = viewHolder.adapterPosition
                    val product = productList[pd]
                    productAdapter.onSwipe(pd)
                    getMagnitDao?.deleteProduct(product)
                    dialog.dismiss()
                    Snackbar.make(root, "Mahsulot o'chirildi!", Snackbar.LENGTH_LONG)
                        .setAction("Bekor qilish",
                            object : View.OnClickListener {
                                override fun onClick(v: View?) {
                                    getMagnitDao?.insertProduct(product)
                                    (productList as ArrayList).add(pd, product)
                                    productAdapter.notifyItemInserted(pd)
                                    productAdapter.notifyItemRangeChanged(pd, productList.size)
                                }

                            }).show()
                }
                view.delete_product_dialog_bekor_qilish.setOnClickListener {
                    dialog.dismiss()
                    val pd = viewHolder.adapterPosition
                    val product = productList[pd]
                    productAdapter.onSwipe(pd)
                    (productList as ArrayList).add(pd, product)
                    productAdapter.notifyItemInserted(pd)
                    productAdapter.notifyItemRangeChanged(pd, productList.size)
                    dialog.dismiss()
                }
                dialog.show()


            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouch)
        itemTouchHelper.attachToRecyclerView(root.product_recycler_view)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_product_menu) {
            findNavController().navigate(R.id.addProductFragment)
        }
        return true
    }


}
