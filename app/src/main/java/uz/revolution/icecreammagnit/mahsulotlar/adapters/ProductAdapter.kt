package uz.revolution.icecreammagnit.mahsulotlar.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.product_item.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.database.AppDatabase
import uz.revolution.icecreammagnit.mahsulotlar.EditProductFragment
import uz.revolution.icecreammagnit.models.Product
import uz.revolution.icecreammagnit.models.Supplier

class ProductAdapter(var productList: List<Product>,var supplierList:List<Supplier>) : RecyclerView.Adapter<ProductAdapter.Vh>(),
    SwipeProduct {
    var chek = true
    val database = AppDatabase.get.getDatabase()
    val getMagnitDao = database.getProductDao()

    inner class Vh(itemview: View) : RecyclerView.ViewHolder(itemview) {
        fun BindView(product: Product) {

            supplierList = getMagnitDao?.getAllSuppliers()!! as ArrayList
            itemView.item_product_name.text = product.name
            itemView.item_product_qolgan_soni.text = product.balance.toString()
            itemView.item_product_karobkada_soni.text = product.totalBox.toString() + "x"
            itemView.item_product_mijoz_uchun_narx_soni.text =
                product.costCustomer.toString() + " so'm"
            itemView.item_product_narx.text = "Narx:   " + product.costDriver.toString() + "so'm"
            itemView.item_product_tamonotchi_name.text = TaminotchiniTop(product.supplierID)

            itemView.setOnClickListener(View.OnClickListener {
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

            itemView.setOnLongClickListener(object : View.OnLongClickListener {
                override fun onLongClick(v: View?): Boolean {
                    var bundle = Bundle()
                    bundle.putSerializable("param1",product)
                    itemView.findNavController().navigate(R.id.editProductFragment,bundle)
                    return true
                }
            })
        }

        fun TaminotchiniTop(id: Int): String {
            var str = ""
            for (i in 0 until supplierList.size) {
                if (supplierList[i].supplierID == id) {
                    str = supplierList[i].name!!
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
        notifyItemRangeChanged(fromPosition,productList.size)
    }
}