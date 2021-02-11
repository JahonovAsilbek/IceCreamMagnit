package uz.revolution.icecreammagnit.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_received_product.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.adapters.ReceivedItemAdapter
import uz.revolution.icecreammagnit.models.Product
import uz.revolution.icecreammagnit.models.ReceivedProducts

private const val ARG_PARAM1 = "received"

class ReceivedProductFragment : Fragment() {

    private var param1: ArrayList<ReceivedProducts>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        param1= bundle?.getSerializable("received") as ArrayList<ReceivedProducts>?
    }

    lateinit var root:View
    lateinit var adapter:ReceivedItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_received_product, container, false)

        adapter= ReceivedItemAdapter(param1!!)
        root.received_rv.adapter=adapter

        return root
    }

//    companion object {
//
//        @JvmStatic
//        fun newInstance(param1: String) =
//            ReceivedProductFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                }
//            }
//    }
}