package uz.revolution.icecreammagnit.mijozlar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_customer_temporary.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.models.CustomerTemporary

class CustomerTemporaryAdapter():RecyclerView.Adapter<CustomerTemporaryAdapter.VH>() {

    private var onLongClick:OnLongClick?=null
    private var customerTemporaryList:ArrayList<CustomerTemporary>?=null

    fun setAdapter(customerTemporaryList: ArrayList<CustomerTemporary>) {
        this.customerTemporaryList=customerTemporaryList
    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(customerTemporary: CustomerTemporary) {
            itemView.product_name.text = customerTemporary.name
            itemView.product_cost.text =customerTemporary.mahsulotlarSummasi.toString()
            itemView.box_number_cost.text="${customerTemporary.box}x${customerTemporary.karobkadaSoni}x${customerTemporary.customerCost}"

            itemView.setOnLongClickListener {
                if (onLongClick != null) {
                    onLongClick!!.onClick(customerTemporary)
                }
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.item_customer_temporary,parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(customerTemporaryList!![position])
    }

    override fun getItemCount(): Int = customerTemporaryList!!.size

    interface OnLongClick{
        fun onClick(customerTemporary: CustomerTemporary)
    }

    fun setOnLongClick(onLongClick: OnLongClick) {
        this.onLongClick=onLongClick
    }

}