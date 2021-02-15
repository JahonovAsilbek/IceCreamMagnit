package uz.revolution.icecreammagnit.haydovchilar.dialogs

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
import uz.revolution.icecreammagnit.models.Temporary

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DrTemporaryEditDialog : DialogFragment() {

    private var param1: Temporary? = null
    private var param2: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Temporary?
            param2 = it.getInt(ARG_PARAM2)
        }
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
                Log.d("TUIT", "box: $box")
                if (onPositiveClick != null && (param2!! + param1!!.numberReceived) >= box.toInt()) {
                    param1!!.numberReceived=Integer.parseInt(box)
                    onPositiveClick!!.onClick(

                        param1!!
                    )
                    dismiss()
                } else {
                    Toast.makeText(
                        root.context,
                        "Eng ko'pi bilan ${param2!!+param1!!.numberReceived} kiritish mumkin!",
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
        root.set_box_et.setText(param1!!.numberReceived.toString())
        root.title.text = param1!!.name
    }

    override fun getTheme(): Int = R.style.RoundedCornersDialog

    companion object {
        @JvmStatic
        fun newInstance(param1: Temporary, param2: Int) =
            DrTemporaryEditDialog().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                    putInt(ARG_PARAM2, param2)
                }
            }
    }

    interface OnPositiveClick {
        fun onClick(temporary: Temporary)
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