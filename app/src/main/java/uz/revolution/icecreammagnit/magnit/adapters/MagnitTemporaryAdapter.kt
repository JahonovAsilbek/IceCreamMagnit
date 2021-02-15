package uz.revolution.icecreammagnit.magnit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_magnit_temporary.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.models.MagnitTemporary

class MagnitTemporaryAdapter : RecyclerView.Adapter<MagnitTemporaryAdapter.VH>() {

    private var magnitTemporaryList: ArrayList<MagnitTemporary>? = null
    private var onLongClick:OnLongClick?=null

    fun setAdapter(magnitTemporaryList: ArrayList<MagnitTemporary>) {
        this.magnitTemporaryList = magnitTemporaryList
    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(magnitTemporary: MagnitTemporary) {
            itemView.product_name.text=magnitTemporary.name
            itemView.total_box.text=magnitTemporary.receivedNumber.toString()

            itemView.setOnLongClickListener {
                if (onLongClick != null) {
                    onLongClick!!.onLongClick(magnitTemporary)
                }
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_magnit_temporary, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(magnitTemporaryList!![position])
    }

    override fun getItemCount(): Int = magnitTemporaryList!!.size

    interface OnLongClick{
        fun onLongClick(magnitTemporary: MagnitTemporary)
    }

    fun setOnLongClick(onLongClick: OnLongClick) {
        this.onLongClick=onLongClick
    }

}