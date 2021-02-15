package uz.revolution.icecreammagnit.mahsulot_qabul_qilish

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.item_bottomsheet.view.*
import kotlinx.android.synthetic.main.received_product.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.mahsulot_qabul_qilish.adapters.ReceivedItemAdapter
import uz.revolution.icecreammagnit.models.ReceivedProducts

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

        adapter.setOnItemClick(object : ReceivedItemAdapter.OnItemClick {
            override fun onClick(receivedProducts: ReceivedProducts) {
                val dialog = BottomSheetDialog(root.context, R.style.SheetDialog)

                val view = layoutInflater.inflate(R.layout.item_bottomsheet, null, false)
                view.date.text = "Sana:  ${receivedProducts.date}"
                view.given_cash.text = "Berilgan summa:  ${receivedProducts.givenCash}"
                view.received_cash.text = "Ja'mi summa:  ${receivedProducts.receivedCash}"
                view.products.text = "Tovarlar: \n${receivedProducts.product}"
                view.total_box.text = "Ja'mi karobka:  ${receivedProducts.totalBox}"

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
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)
                }
                dialog.setContentView(view)
                dialog.show()
            }

        })

        return root
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

}