package uz.revolution.icecreammagnit.magnit

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.item_bottomsheet_magnit.view.*
import kotlinx.android.synthetic.main.magnit.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.daos.MagnitDao
import uz.revolution.icecreammagnit.database.AppDatabase
import uz.revolution.icecreammagnit.magnit.adapters.MagnitItemAdapter
import uz.revolution.icecreammagnit.models.Magnit


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MagnitFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        database = AppDatabase.get.getDatabase()
        magnitDao = database!!.getProductDao()
        adapter = MagnitItemAdapter()
    }

    lateinit var root: View
    private var database: AppDatabase? = null
    private var magnitDao: MagnitDao? = null
    private var magnitList: ArrayList<Magnit>? = null
    private var adapter: MagnitItemAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.magnit, container, false)
        loadData()
        loadAdapters()
        addNewClick()
        itemClick()
        return root
    }

    private fun itemClick() {
        adapter?.setOnItemClick(object : MagnitItemAdapter.OnItemClick {
            @SuppressLint("SetTextI18n")
            override fun onClick(magnit: Magnit) {
                val dialog = BottomSheetDialog(root.context, R.style.SheetDialog)
                val view = layoutInflater.inflate(R.layout.item_bottomsheet_magnit, null, false)
                view.date.text = "Sana: ${magnit.date}"
                view.products.text = "Tovarlar: ${magnit.product}"
                view.total_box.text = "\n\nJami karobka: ${magnit.totalBox}"
                view.share.setOnClickListener {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            view.date.text.toString()
                                    + "    Magnit\n\n" +
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
    }

    private fun addNewClick() {
        root.magnit_set_new.setOnClickListener {
            findNavController().navigate(R.id.magnitTemporaryFragment)
        }
    }

    private fun loadData() {
        magnitList = ArrayList()
        magnitList = magnitDao?.getAllProductsMagnit() as ArrayList
    }

    private fun loadAdapters() {
        magnitList!!.reverse()
        adapter?.setAdapter(magnitList!!)
        root.magnit_rv.adapter = adapter
        adapter?.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        loadData()
        loadAdapters()
    }
}