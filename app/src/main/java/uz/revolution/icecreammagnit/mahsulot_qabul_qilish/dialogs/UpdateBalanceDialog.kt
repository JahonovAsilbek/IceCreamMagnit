package uz.revolution.icecreammagnit.mahsulot_qabul_qilish.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.update_balance_dialog.view.*
import uz.revolution.icecreammagnit.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "product"
private const val ARG_PARAM2 = "param2"

class UpdateBalanceDialog : DialogFragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var root: View
    private var onAddClick: OnAddClick? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.update_balance_dialog, container, false)
        dialog?.requestWindowFeature(STYLE_NORMAL)
        isCancelable = false

        root.update_balance_btn.setOnClickListener {
            if (root.update_balance_et.text.toString().trim().isNotEmpty()) {
                val givenCash = root.update_balance_et.text.toString()
                if (onAddClick != null) {
                    onAddClick!!.onClick(givenCash.toInt())
                }
                dismiss()
            } else {
                Toast.makeText(root.context, "Summani kiriting!", Toast.LENGTH_LONG).show()
            }
        }

        root.update_balance_btn_cancel.setOnClickListener {
            dismiss()
        }

        return root
    }


    override fun getTheme(): Int = R.style.RoundedCornersDialog

    interface OnAddClick {
        fun onClick(givenCash: Int)
    }

    fun setOnAddClick(onAddClick: OnAddClick) {
        this.onAddClick = onAddClick
    }

//    companion object {
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            UpdateBalanceDialog().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}