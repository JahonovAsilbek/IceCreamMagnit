package uz.revolution.icecreammagnit.mijozlar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_choose_customer_product.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.models.Product

class ChooseProductCustomerAdapter() :
    RecyclerView.Adapter<ChooseProductCustomerAdapter.VH>() {

    private var productList:ArrayList<Product>?=null

    fun setAdapter(productList: ArrayList<Product>) {
        this.productList=productList
    }

    private var onItemCLick:OnItemCLick?=null

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(product: Product,position: Int) {
            itemView.product_name.text = product.name
            itemView.balance.text = "Bazada soni: ${product.balance}"
            itemView.customer_cost.text = "Narxi: ${product.costCustomer}"
            itemView.karobkada_soni.text = "Karobkada soni: ${product.totalBox} ta"

            itemView.setOnClickListener {
                if (onItemCLick != null) {
                    onItemCLick!!.onClick(product,position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_choose_customer_product, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(productList!![position],position)
    }

    override fun getItemCount(): Int = productList!!.size

    interface OnItemCLick{
        fun onClick(product: Product,position: Int)
    }

    fun setOnItemClick(onItemCLick: OnItemCLick) {
        this.onItemCLick=onItemCLick
    }

}