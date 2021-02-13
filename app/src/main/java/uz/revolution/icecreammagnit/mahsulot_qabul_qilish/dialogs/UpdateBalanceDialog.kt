package uz.revolution.icecreammagnit.mahsulot_qabul_qilish.dialogs

import android.app.ActionBar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.update_balance_dialog.view.*
import uz.revolution.icecreammagnit.R

private const val ARG_PARAM1 = "product"

class UpdateBalanceDialog : DialogFragment() {

    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
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
        root.update_balance_et.requestFocus()
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

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

        root.tv.text="Mahsulotlar summasi: ${param1}"

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

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            UpdateBalanceDialog().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}