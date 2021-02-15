package uz.revolution.icecreammagnit.mijozlar.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_customer_complete_dialog.view.*
import kotlinx.android.synthetic.main.fragment_customer_complete_dialog.view.tv
import kotlinx.android.synthetic.main.fragment_customer_edit.view.*
import uz.revolution.icecreammagnit.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CustomerCompleteDialog : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: Int = 0
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var root: View
    private var onPositiveClick: OnPositiveClick? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_customer_complete_dialog, container, false)
        dialog?.requestWindowFeature(STYLE_NORMAL)
        isCancelable = false
        root.complete_et.requestFocus()
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        loadDataToView()
        onOkClick()
        onCancelClick()

        return root
    }

    override fun getTheme(): Int = R.style.RoundedCornersDialog

    private fun loadDataToView() {
        root.tv.text = "Mahsulotlar summasi: $param1"
    }

    private fun onCancelClick() {
        root.complete_cancel.setOnClickListener {
            dismiss()
        }
    }

    private fun onOkClick() {
        root.complete_ok.setOnClickListener {
            if (root.complete_et.text.toString().trim().isNotEmpty()) {
                val insertedCash = root.complete_et.text.toString()
                if (onPositiveClick != null) {
                    onPositiveClick!!.onClick(insertedCash.toInt())
                }
                dismiss()
            } else {
                Toast.makeText(root.context, "Summani kiriting!", Toast.LENGTH_LONG).show()
            }
        }
    }

    interface OnPositiveClick {
        fun onClick(receivedCash: Int)
    }

    fun setOnPositiveClick(onPositiveClick: OnPositiveClick) {
        this.onPositiveClick = onPositiveClick
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: Int, param2: String) =
            CustomerCompleteDialog().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}