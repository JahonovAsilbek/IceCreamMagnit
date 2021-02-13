package uz.revolution.icecreammagnit.mahsulot_qabul_qilish.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_set_quantity_product.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.models.Product

class UpdateProductBalanceAdapter(var productList: ArrayList<Product>) :
    RecyclerView.Adapter<UpdateProductBalanceAdapter.VH>() {

    private var onItemClick:OnItemClick?=null

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(product: Product,position: Int) {
            itemView.product_name.text = product.name
            itemView.product_name.tag=product.id
//            itemView.update_balance.setOnClickListener {
//                if (onItemClick != null) {
//                    onItemClick!!.onClick(product,position)
//                }
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_set_quantity_product, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(productList[position],position)
    }

    override fun getItemCount(): Int = productList.size

    interface OnItemClick{
        fun onClick(product: Product, position: Int)
    }

    fun setOnItemClick(onItemClick: OnItemClick) {
        this.onItemClick=onItemClick
    }
}