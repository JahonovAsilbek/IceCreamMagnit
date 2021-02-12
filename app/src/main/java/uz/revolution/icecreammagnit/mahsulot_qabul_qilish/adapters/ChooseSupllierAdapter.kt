package uz.revolution.icecreammagnit.mahsulot_qabul_qilish.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_choose_supplier.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.models.Supplier

class ChooseSupllierAdapter(var supplierList: ArrayList<Supplier>) :
    RecyclerView.Adapter<ChooseSupllierAdapter.VH>() {

    private var onItemClick: OnItemClick? = null

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(supplier: Supplier) {
            itemView.choose_supplier_tv.text = supplier.name

            itemView.setOnClickListener {
                if (onItemClick != null) {
                    onItemClick!!.onClick(supplier.supplierID)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_choose_supplier, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(supplierList[position])
    }

    override fun getItemCount(): Int = supplierList.size

    interface OnItemClick {
        fun onClick(id: Int)
    }

    fun setOnItemClick(onItemClick: OnItemClick) {
        this.onItemClick = onItemClick
    }
}