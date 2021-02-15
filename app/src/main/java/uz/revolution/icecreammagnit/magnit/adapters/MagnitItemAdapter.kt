package uz.revolution.icecreammagnit.magnit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_magnit.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.models.Magnit

class MagnitItemAdapter : RecyclerView.Adapter<MagnitItemAdapter.VH>() {

    private var magnitList: ArrayList<Magnit>? = null
    private var onItemClick:OnItemClick?=null

    fun setAdapter(magnitList: ArrayList<Magnit>) {
        this.magnitList=magnitList
    }

    inner class VH(itemView:View):RecyclerView.ViewHolder(itemView){
        fun onBind(magnit: Magnit) {
            itemView.date.text="Sana: ${magnit.date}"
            itemView.total_box.text="Jami karobka: ${magnit.totalBox}"

            itemView.setOnClickListener {
                if (onItemClick != null) {
                    onItemClick!!.onClick(magnit)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.item_magnit, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(magnitList!![position])
    }

    override fun getItemCount(): Int =magnitList!!.size

    interface OnItemClick{
        fun onClick(magnit: Magnit)
    }

    fun setOnItemClick(onItemClick: OnItemClick) {
        this.onItemClick=onItemClick
    }
}