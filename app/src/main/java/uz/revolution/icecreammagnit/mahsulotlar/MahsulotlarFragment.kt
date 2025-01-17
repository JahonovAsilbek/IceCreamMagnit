package uz.revolution.icecreammagnit.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MahsulotlarFragment : Fragment() {

    lateinit var root: View
    lateinit var productAdapter: ProductAdapter
    lateinit var productList: List<Product>
    lateinit var suppliertList: List<Supplier>
    val database = AppDatabase.get.getDatabase()
    val getMagnitDao = database.getProductDao()
    var productsStr: String = ""
    var umumiy_qolgan_soni = 0

    fun loadData() {
        productList = ArrayList()
        productList = getMagnitDao?.getProductList()!!
        suppliertList = getMagnitDao.getAllSuppliers()
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
        loadmyStr()
        Log.d("TTTT", "onCreateView: $umumiy_qolgan_soni")
        onShareClick()

        loadSwipeFun()

        return root
    }

    private fun onShareClick() {
        root.mahsulot_share_btn.setOnClickListener {
            if (productList.isNotEmpty()) {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Mahsulotlar | " + getCurrentDate() + "\n\n" + productsStr + "\n\nJami karobka soni: " + umumiy_qolgan_soni
                    )
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            } else {
                Toast.makeText(root.context, "Oldin ma'lumot kiriting!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadmyStr() {
        for (i in 0 until productList.size) {
            productsStr += productList[i].name + "   ->  " + productList[i].balance + "\n"

            umumiy_qolgan_soni += productList[i].balance
        }
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
                dialog.setCancelable(false)
                dialog.setView(view)

                view.delete_product_dialog_davom_etish_.setOnClickListener {
                    val pd = viewHolder.adapterPosition
                    val product = productList[pd]
                    productAdapter.onSwipe(pd)
                    getMagnitDao?.deleteProduct(product)
                    dialog.dismiss()
                    val snackbar = Snackbar.make(root, "Mahsulot o'chirildi!", Snackbar.LENGTH_LONG)
                    snackbar.setAction("Bekor qilish",
                        object : View.OnClickListener {
                            override fun onClick(v: View?) {
                                getMagnitDao?.insertProduct(product)
                                (productList as ArrayList).add(pd, product)
                                productAdapter.notifyItemInserted(pd)
                                productAdapter.notifyItemRangeChanged(pd, productList.size)
                            }

                        })
                    val sView = snackbar.view
                    sView.background = resources.getDrawable(R.drawable.btn_back)
                    snackbar.show()
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

    private fun getCurrentDate(): String {
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
        val currentDateAndTime: String = simpleDateFormat.format(Date())
        return currentDateAndTime
    }

}
