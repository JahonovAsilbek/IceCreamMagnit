package uz.revolution.icecreammagnit.mijozlar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_received.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.models.Customer

class CustomerAdapter :
    RecyclerView.Adapter<CustomerAdapter.VH>() {

    private var onItemClick:OnItemClick?=null
    private var customerList:ArrayList<Customer>?=null

    fun setAdapter(customerList: ArrayList<Customer>) {
        this.customerList=customerList
    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(customer: Customer) {
            itemView.date.text = "Sana: ${customer.date}"
            itemView.received_cash.text = "Olingan summa: ${customer.receivedCash}"
            itemView.total_cash.text="Berilgan tovarlar: ${customer.givenCash}"

            itemView.setOnClickListener {
                if (onItemClick != null) {
                    onItemClick!!.onClick(customer)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_received, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(customerList!![position])
    }

    override fun getItemCount(): Int = customerList!!.size

    interface OnItemClick{
        fun onClick(customer: Customer)
    }

    fun setOnItemClick(onItemClick: OnItemClick) {
        this.onItemClick=onItemClick
    }
}