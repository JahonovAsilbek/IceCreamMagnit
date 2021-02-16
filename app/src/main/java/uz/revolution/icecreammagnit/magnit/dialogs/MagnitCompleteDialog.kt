package uz.revolution.icecreammagnit.magnit.dialogs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_customer_edit.view.*
import kotlinx.android.synthetic.main.fragment_magnit_complete_dialog.view.*
import uz.revolution.icecreammagnit.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MagnitCompleteDialog.newInstance] factory method to
 * create an instance of this fragment.
 */
class MagnitCompleteDialog : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun getTheme(): Int = R.style.RoundedCornersDialog
    lateinit var root:View
    private var onClick:OnClick?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_magnit_complete_dialog, container, false)
        dialog?.requestWindowFeature(STYLE_NORMAL)
        isCancelable = true
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        root.complete_ok.setOnClickListener {
            if (onClick != null) {
                onClick!!.onClick()
                dismiss()
            }
        }


        return root
    }

    interface OnClick{
        fun onClick()
    }

    fun setOnClick(onClick: OnClick) {
        this.onClick=onClick
    }
}