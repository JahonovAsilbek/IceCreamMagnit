package uz.revolution.icecreammagnit.mijozlar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.models.Customer
import uz.revolution.icecreammagnit.models.ReceivedProducts

class CustomerItemFragment : Fragment() {

    private var param1: ArrayList<Customer>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        param1 = bundle?.getSerializable("customer") as ArrayList<Customer>?
    }

    lateinit var root:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_customer, container, false)



        return root
    }


}