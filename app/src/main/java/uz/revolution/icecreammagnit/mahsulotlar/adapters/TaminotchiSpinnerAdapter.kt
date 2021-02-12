package uz.revolution.icecreammagnit.mahsulotlar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.taminotchi_spinner_item.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.models.Supplier

class TaminotchiSpinnerAdapter(var list:List<Supplier>):BaseAdapter() {
    override fun getCount(): Int =list.size

    override fun getItem(position: Int): Supplier=list[position]

    override fun getItemId(position: Int): Long =position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View
        if (convertView == null) {
            view =
                LayoutInflater.from(parent?.context).inflate(R.layout.taminotchi_spinner_item, parent, false)
        } else {
            view=convertView
        }
        view.taminotchi_spinner_item.setText(list[position].name)
        return view
    }
}