package uz.revolution.icecreammagnit.mahsulotlar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.delete_prdct_item_dialog.view.*
import kotlinx.android.synthetic.main.fragment_mahsulotlar.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.mahsulotlar.adapters.ProductAdapter
import uz.revolution.icecreammagnit.models.Product

class MahsulotlarFragment : Fragment() {

    lateinit var root: View
    lateinit var productAdapter: ProductAdapter
    lateinit var productList: List<Product>

    fun loadData() {
        productList = ArrayList()
//        val database = AppDatabase.get.getDatabase()
//        val getMagnitDao = database.getProductDao()
//        productList= getMagnitDao?.getProductList()!!
        (productList as ArrayList<Product>).add(
            Product(
                1,
                1,
                "Grand s sgushonka",
                2500,
                2700,
                20,
                30
            )
        )
        (productList as ArrayList<Product>).add(
            Product(
                1,
                3,
                "Grand s sgushonka",
                2500,
                2700,
                20,
                30
            )
        )
        (productList as ArrayList<Product>).add(
            Product(
                0,
                4,
                "Grand s sgushonka",
                2500,
                2700,
                20,
                30
            )
        )
        (productList as ArrayList<Product>).add(
            Product(
                2,
                0,
                "Grand s sgushonka",
                2500,
                2700,
                20,
                30
            )
        )
        (productList as ArrayList<Product>).add(
            Product(
                2,
                2,
                "Grand s sgushonka",
                2500,
                2700,
                20,
                30
            )
        )
        (productList as ArrayList<Product>).add(
            Product(
                1,
                1,
                "Grand s sgushonka",
                2500,
                2700,
                20,
                30
            )
        )
        (productList as ArrayList<Product>).add(
            Product(
                4,
                3,
                "Grand s sgushonka",
                2500,
                2700,
                20,
                30
            )
        )
        (productList as ArrayList<Product>).add(
            Product(
                1,
                2,
                "Grand s sgushonka",
                2500,
                2700,
                20,
                30
            )
        )

        productAdapter = ProductAdapter(productList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loadData()
        root = inflater.inflate(R.layout.fragment_mahsulotlar, container, false)
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
                val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END

                return makeMovementFlags(0,swipeFlags)
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

                view.delete_product_dialog_davom_etish_.setOnClickListener{
                    val pd = viewHolder.adapterPosition
                    val product = productList[pd]
                    productAdapter.onSwipe(pd)
                    dialog.dismiss()
                    Snackbar.make(root,"Mahsulot o'chirildi!",Snackbar.LENGTH_LONG).setAction("Bekor qilish",
                        object : View.OnClickListener {
                            override fun onClick(v: View?) {
                                (productList as ArrayList).add(pd, product)
                                productAdapter.notifyItemInserted(pd)
                                productAdapter.notifyItemRangeChanged(pd,productList.size)
                            }

                        }).show()
                }
                view.delete_product_dialog_bekor_qilish.setOnClickListener{
                    dialog.dismiss()
                    val pd = viewHolder.adapterPosition
                    val product = productList[pd]
                    productAdapter.onSwipe(pd)
                    (productList as ArrayList).add(pd, product)
                    productAdapter.notifyItemInserted(pd)
                    productAdapter.notifyItemRangeChanged(pd,productList.size)
                    dialog.dismiss()
                }
                dialog.show()




            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouch)
        itemTouchHelper.attachToRecyclerView(root.product_recycler_view)
    }
}
