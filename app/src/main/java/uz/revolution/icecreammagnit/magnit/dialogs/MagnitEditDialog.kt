package uz.revolution.icecreammagnit.mijozlar.dialogs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_customer_edit.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.models.CustomerTemporary
import uz.revolution.icecreammagnit.models.MagnitTemporary

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MagnitEditDialog : DialogFragment() {

    private var param1: MagnitTemporary? = null
    private var param2: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as MagnitTemporary?
            param2 = it.getInt(ARG_PARAM2)
        }
        Log.d("AAAA", "Edit Dialog box: $param2")
    }

    lateinit var root: View
    private var onDeleteClick: OnDeleteClick? = null
    private var onPositiveClick: OnPositiveClick? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_customer_edit, container, false)
        dialog?.requestWindowFeature(STYLE_NORMAL)
        isCancelable = false
        root.set_box_et.requestFocus()
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        loadDataToView()
        onOkClick()
        onCancelClick()
        onDeleteClick()

        return root
    }

    private fun onDeleteClick() {
        root.delete_btn.setOnClickListener {
            if (onDeleteClick != null) {
                onDeleteClick!!.onClick()
                dismiss()
            }
        }
    }

    private fun onCancelClick() {
        root.cancel_btn.setOnClickListener {
            dismiss()
        }
    }

    private fun onOkClick() {
        root.ok_btn.setOnClickListener {
            if (root.set_box_et.text.toString().trim().isNotEmpty()) {
                val box = root.set_box_et.text.toString()
                if (onPositiveClick != null && (param2!! + param1!!.receivedNumber) >= box.toInt()) {
                    onPositiveClick!!.onClick(
                        MagnitTemporary(
                            param1!!.name.toString(),
                            box.toInt()
                        )
                    )
                    dismiss()
                } else {
                    Toast.makeText(
                        root.context,
                        "Eng ko'pi bilan ${param2!!+param1!!.receivedNumber} kiritish mumkin!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(root.context, "Sonni kiriting!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun loadDataToView() {
        root.tv.text = param2.toString()
        root.set_box_et.setText(param1!!.receivedNumber.toString())
        root.title.text = param1!!.name
    }

    override fun getTheme(): Int = R.style.RoundedCornersDialog

    companion object {
        @JvmStatic
        fun newInstance(param1: MagnitTemporary, param2: Int) =
            MagnitEditDialog().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                    putInt(ARG_PARAM2, param2)
                }
            }
    }

    interface OnPositiveClick {
        fun onClick(magnitTemporary: MagnitTemporary)
    }

    fun setOnPositiveClick(onPositiveClick: OnPositiveClick) {
        this.onPositiveClick = onPositiveClick
    }

    interface OnDeleteClick {
        fun onClick()
    }

    fun setOnDeleteClick(onDeleteClick: OnDeleteClick) {
        this.onDeleteClick = onDeleteClick
    }
}