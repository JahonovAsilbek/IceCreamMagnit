package uz.revolution.icecreammagnit.mijozlar.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_customer_set_box_dialog.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.models.Product

private const val ARG_PARAM1 = "dialog_"
private const val ARG_PARAM2 = "param2"

class CustomerSetBoxDialog : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: Product? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Product?
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var root: View
    private var onPostiveClick: OnPostiveClick? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_customer_set_box_dialog, container, false)
        dialog?.requestWindowFeature(STYLE_NORMAL)
        isCancelable = false
        root.set_box_et.requestFocus()
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        loadDataToView()
        okClick()
        cancelClick()

        return root
    }

    override fun getTheme(): Int = R.style.RoundedCornersDialog

    private fun cancelClick() {
        root.cancel_btn.setOnClickListener {
            dismiss()
        }
    }

    private fun okClick() {
        root.ok_btn.setOnClickListener {
            if (root.set_box_et.text.toString().trim().isNotEmpty()) {
                val box = root.set_box_et.text.toString()
                box.toInt() != 0
                if (box.toInt() != 0) {
                    if (onPostiveClick != null && box.toInt() <= param1!!.balance) {
                        onPostiveClick!!.onClick(box.toInt())
                        dismiss()
                    } else {
                        Toast.makeText(
                            root.context,
                            "Eng ko'pi bilan ${param1!!.balance} kiritish mumkin!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(root.context, "0 kiritish mumkin emas", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                Toast.makeText(root.context, "Sonini kiriting!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun loadDataToView() {
        root.title.text = param1?.name
        root.tv.text = param1?.balance.toString()
    }

    interface OnPostiveClick {
        fun onClick(box: Int)
    }

    fun setOnPositiveClick(onPostiveClick: OnPostiveClick) {
        this.onPostiveClick = onPostiveClick
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: Product, param2: String) =
            CustomerSetBoxDialog().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}