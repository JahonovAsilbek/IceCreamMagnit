package uz.revolution.icecreammagnit.haydovchilar.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.driver_history_item.view.*
import kotlinx.android.synthetic.main.item_bottomsheet.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.models.Driver

class DriverItemAdapter(var driverList:List<Driver>) : RecyclerView.Adapter<DriverItemAdapter.Vh>() {
    inner class Vh(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(driver: Driver) {
            var haydovchi_serial=driver.serialNumber
            itemView.driver_hstry_date.text = "$haydovchi_serial - Haydovchi | "+driver.date
            itemView.driver_hstry_berilgan_summa.text="Berilgan tovarlar summasi: "+driver.givenCash
            itemView.driver_hstry_olingan_summa.text = "Olingan summa: " + driver.receivedCash
            itemView.driver_hstry_berilgan_tovarlar.text = "Tovarlar:   " + driver.product


            itemView.setOnClickListener{
                val dialog = BottomSheetDialog(itemView.context, R.style.SheetDialog)

                val view = LayoutInflater.from(itemView.context).inflate(R.layout.item_bottomsheet, null, false)
                view.date.text = "$haydovchi_serial - Haydovchi |  ${driver.date}"
                view.given_cash.text = "Berilgan tovarlar summasi:  ${driver.givenCash}"
                view.received_cash.text = "Olingan summa:  ${driver.receivedCash}"
                view.products.text = "Tovarlar: ${driver.product}"
                view.total_box.text = "Jami karobka:  ${driver.totalBox.toString()}"

                view.share.setOnClickListener {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            view.date.text.toString()
                                    + "\n" +
                                    view.given_cash.text.toString()
                                    + "\n" +
                                    view.received_cash.text.toString()
                                    + "\n" +
                                    view.products.text.toString()
                                    + "\n\n" +
                                    view.total_box.text.toString()
                        )
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, "Ulashish")
                    itemView.context.startActivity(shareIntent)
                }
                dialog.setContentView(view)
                dialog.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(LayoutInflater.from(parent.context).inflate(R.layout.driver_history_item,parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(driverList[position])
    }

    override fun getItemCount(): Int = driverList.size
}