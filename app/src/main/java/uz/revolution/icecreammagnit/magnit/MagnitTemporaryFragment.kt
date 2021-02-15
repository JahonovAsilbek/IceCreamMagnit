package uz.revolution.icecreammagnit.magnit

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.magnit_temporary.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.daos.MagnitDao
import uz.revolution.icecreammagnit.database.AppDatabase
import uz.revolution.icecreammagnit.magnit.adapters.MagnitTemporaryAdapter
import uz.revolution.icecreammagnit.magnit.dialogs.MagnitCompleteDialog
import uz.revolution.icecreammagnit.mijozlar.dialogs.CustomerCompleteDialog
import uz.revolution.icecreammagnit.mijozlar.dialogs.MagnitEditDialog
import uz.revolution.icecreammagnit.models.Magnit
import uz.revolution.icecreammagnit.models.MagnitTemporary
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MagnitTemporaryFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        (activity as AppCompatActivity).supportActionBar?.title = "${getCurrentDate()} | Magnit"
        database = AppDatabase.get.getDatabase()
        magnitDao = database?.getProductDao()
        adapter = MagnitTemporaryAdapter()
    }

    lateinit var root: View
    private var database: AppDatabase? = null
    private var magnitDao: MagnitDao? = null
    private var magnitTemporaryList: ArrayList<MagnitTemporary>? = null
    lateinit var adapter: MagnitTemporaryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.magnit_temporary, container, false)

        loadData()
        loadAdapters()
        floatActionButtonClick()
        totalBox()
        completeClick()
        onEditClick()
        return root
    }

    private fun completeClick() {
        root.magnit_temporary_complete.setOnClickListener {
            var tovar = ""
            var totalBox = 0
            var totalCash = 0
            if (magnitTemporaryList != null) {
                for (i in 0 until magnitTemporaryList!!.size) {
                    tovar +=
                        "\n${magnitTemporaryList!![i].name}   ${magnitTemporaryList!![i].receivedNumber}x${
                            magnitDao?.getProductByName(magnitTemporaryList!![i].name!!)?.totalBox
                        }x${magnitDao?.getProductByName(magnitTemporaryList!![i].name!!)?.costCustomer}"
                    totalBox += magnitTemporaryList!![i].receivedNumber
                    totalCash += magnitTemporaryList!![i].receivedNumber * magnitDao?.getProductByName(
                        magnitTemporaryList!![i].name!!
                    )?.totalBox!!.toInt() * magnitDao?.getProductByName(magnitTemporaryList!![i].name!!)?.costCustomer!!.toInt()
                }

                val beginTransaction = childFragmentManager.beginTransaction()
                val dialog =MagnitCompleteDialog()
                dialog.show(beginTransaction,"complete")
                dialog.setOnClick(object :MagnitCompleteDialog.OnClick{
                    override fun onClick() {
                        magnitDao?.insertMagnit(Magnit(getCurrentDate(), tovar, totalBox))
                        magnitDao?.deleteAllFromMagnitTemporary()
                        findNavController().popBackStack()
                    }

                })


            } else {
                Toast.makeText(root.context, "Oldin mahsulot tanlang!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onEditClick() {
        adapter.setOnLongClick(object : MagnitTemporaryAdapter.OnLongClick {
            override fun onLongClick(magnitTemporary: MagnitTemporary) {
                val data = magnitTemporary
                val beginTransaction = childFragmentManager.beginTransaction()
                val dialog = MagnitEditDialog.newInstance(
                    magnitTemporary,
                    magnitDao!!.getProductByName(magnitTemporary.name.toString()).balance
                )
                dialog.show(beginTransaction, "editDialog")
                dialog.setOnPositiveClick(object : MagnitEditDialog.OnPositiveClick {
                    override fun onClick(magnitTemporary: MagnitTemporary) {

                        magnitDao?.updateMagnitTemporary(magnitTemporary.receivedNumber, data.id)
                        val deltaBox = data.receivedNumber - magnitTemporary.receivedNumber
                        magnitDao?.addBalance(
                            deltaBox,
                            magnitDao!!.getProductByName(magnitTemporary.name.toString()).id
                        )
                        loadData()
                        loadAdapters()
                    }
                })
                dialog.setOnDeleteClick(object : MagnitEditDialog.OnDeleteClick {
                    override fun onClick() {
                        magnitDao?.addBalance(
                            data.receivedNumber,
                            magnitDao!!.getProductByName(magnitTemporary.name.toString()).id
                        )
                        magnitDao?.deleteProductFromMagnitTemporary(data)
                        adapter.notifyDataSetChanged()
                        Toast.makeText(root.context, "O'chirildi!", Toast.LENGTH_SHORT).show()
                        loadData()
                        loadAdapters()
                    }
                })
            }

        })
    }

    @SuppressLint("SetTextI18n")
    private fun totalBox() {
        var totalBox = 0
        for (i in magnitDao!!.getAllMagnitTemporary().indices) {
            totalBox += magnitDao!!.getAllMagnitTemporary()[i].receivedNumber
        }
        root.magnit_temporary_complete.text = "Yakunlash: $totalBox karobka"
    }

    private fun floatActionButtonClick() {
        root.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.chooseProductMagnit)
        }
    }

    private fun loadAdapters() {
        magnitTemporaryList!!.reverse()
        adapter.setAdapter(magnitTemporaryList!!)
        root.magnit_temporary_rv.adapter = adapter
        adapter.notifyDataSetChanged()
        totalBox()
    }

    private fun loadData() {
        magnitTemporaryList = ArrayList()
        magnitTemporaryList = magnitDao?.getAllMagnitTemporary() as ArrayList
    }

    override fun onResume() {
        super.onResume()
        loadData()
        loadAdapters()
        (activity as AppCompatActivity).supportActionBar?.title = "${getCurrentDate()} | Magnit"
    }

    private fun getCurrentDate(): String {
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
        return simpleDateFormat.format(Date())
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MagnitTemporaryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}