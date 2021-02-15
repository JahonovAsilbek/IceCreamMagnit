package uz.revolution.icecreammagnit.haydovchilar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.driver_temporary_item.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.mijozlar.adapters.CustomerTemporaryAdapter
import uz.revolution.icecreammagnit.models.CustomerTemporary
import uz.revolution.icecreammagnit.models.Temporary

class DrTemporaryAdapter() :
    RecyclerView.Adapter<DrTemporaryAdapter.Vh>() {

    private var onLongClick: OnLongClick?=null
    private var drTemList:ArrayList<Temporary>?=null

    fun setAdapter(drTemList: ArrayList<Temporary>) {
        this.drTemList=drTemList
    }

    inner class Vh(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun MyBind(temporary: Temporary) {
            itemView.driver_temporary_product_name.text = temporary.name
            itemView.driver_temporary_product_cost.text =
                (temporary.numberReceived * temporary.totalBox * temporary.cost).toString() + "so'm"
            itemView.driver_temporary_box_number_cost.text =
                "${temporary.numberReceived}x${temporary.totalBox}x${temporary.cost}"

            itemView.setOnLongClickListener {
                if (onLongClick != null) {
                    onLongClick!!.onClick(temporary)
                }
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.driver_temporary_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.MyBind(drTemList!![position])
    }

    override fun getItemCount(): Int = drTemList!!.size


    interface OnLongClick{
        fun onClick(temporary: Temporary)
    }

    fun setOnLongClick(onLongClick: OnLongClick) {
        this.onLongClick=onLongClick
    }
}