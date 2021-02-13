package uz.revolution.icecreammagnit.mahsulot_qabul_qilish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.received_product.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.mahsulot_qabul_qilish.adapters.ReceivedItemAdapter
import uz.revolution.icecreammagnit.models.ReceivedProducts
import java.util.*
import kotlin.collections.ArrayList

class ReceivedProductFragment : Fragment() {

    private var param1: ArrayList<ReceivedProducts>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        param1 = bundle?.getSerializable("received") as ArrayList<ReceivedProducts>?
    }

    lateinit var root: View
    lateinit var adapter: ReceivedItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.received_product, container, false)

        param1!!.reverse()
        adapter = ReceivedItemAdapter(param1!!)
        root.received_rv.adapter = adapter
        adapter.notifyDataSetChanged()

        return root
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

}