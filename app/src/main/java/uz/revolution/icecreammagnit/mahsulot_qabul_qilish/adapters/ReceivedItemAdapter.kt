package uz.revolution.icecreammagnit.mahsulot_qabul_qilish.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_received.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.models.ReceivedProducts

class ReceivedItemAdapter(var receivedList: ArrayList<ReceivedProducts>) :
    RecyclerView.Adapter<ReceivedItemAdapter.VH>() {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun onBind(receivedProducts: ReceivedProducts) {
            itemView.date.text="Sana: ${receivedProducts.date}"
            itemView.received_cash.text="Olingan summa: ${receivedProducts.receivedCash}"
            itemView.total_cash.text="Ja'mi summa: ${receivedProducts.givenCash}"
            itemView.products.text="Tovarlar: ${receivedProducts.product}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.item_received,parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(receivedList[position])
    }

    override fun getItemCount(): Int = receivedList.size
}