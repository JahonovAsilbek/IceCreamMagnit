package uz.revolution.icecreammagnit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.product_item.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.database.AppDatabase
import uz.revolution.icecreammagnit.models.Product
import uz.revolution.icecreammagnit.models.Supplier

class ProductAdapter(var productList: List<Product>) : RecyclerView.Adapter<ProductAdapter.Vh>(),SwipeProduct {
    var chek = true
    val database = AppDatabase.get.getDatabase()
    val getMagnitDao = database.getProductDao()
    lateinit var list:List<Supplier>
    inner class Vh(itemview: View) : RecyclerView.ViewHolder(itemview) {
        fun BindView(product: Product) {

            list = getMagnitDao?.getAllSuppliers()!!


            itemView.item_product_name.text = product.name
            itemView.item_product_qolgan_soni.text = product.balance.toString()
            itemView.item_product_karobkada_soni.text = product.totalBox.toString()
            itemView.item_product_mijoz_uchun_narx_soni.text =
                product.costCustomer.toString() + "so'm"
            itemView.item_product_narx.text = "Narx:   " + product.costDriver.toString() + "so'm"
            itemView.item_product_tamonotchi_name.text = TaminotchiniTop(product.supplierID)

            itemView.setOnLongClickListener(View.OnLongClickListener {
                if (chek) {
                    itemView.item_product_mijoz_uchun_layout.visibility = View.VISIBLE
                    val mymijozanim_ochil =
                        AnimationUtils.loadAnimation(it.context, R.anim.product_item_mijoz_uchun)
                    itemView.item_product_mijoz_uchun_layout.startAnimation(mymijozanim_ochil)
                    chek = false
                } else {

                    val mymijozanim_yopil =
                        AnimationUtils.loadAnimation(
                            it.context,
                            R.anim.product_item_mijoz_yopilishi
                        )
                    itemView.item_product_mijoz_uchun_layout.startAnimation(mymijozanim_yopil)
                    itemView.item_product_mijoz_uchun_layout.visibility = View.GONE
                    chek = true
                }

                true
            })
        }
        fun TaminotchiniTop(id: Int): String {
            var str = ""
            for (i in 0 until list.size) {
                if (list[i].supplierID == id) {
                    str=list[i].name
                }
            }
            return str
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.BindView(productList[position])
    }

    override fun getItemCount(): Int = productList.size


    override fun onSwipe(fromPosition: Int) {
        (productList as ArrayList).removeAt(fromPosition)
        notifyItemRemoved(fromPosition)
    }
}