package uz.revolution.icecreammagnit.mijozlar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.models.Customer

class CustomerAdapter(var customerList: ArrayList<Customer>) :
    RecyclerView.Adapter<CustomerAdapter.VH>() {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(customer: Customer) {
            
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_received, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

    }

    override fun getItemCount(): Int = customerList.size
}