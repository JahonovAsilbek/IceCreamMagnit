package uz.revolution.icecreammagnit.mahsulotlar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.edit_product.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.database.AppDatabase
import uz.revolution.icecreammagnit.models.Product

private const val ARG_PARAM1 = "param1"

class EditProductFragment : Fragment() {
    private var product: Product? = null

    lateinit var root: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            product = it.getSerializable(ARG_PARAM1) as Product?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.edit_product, container, false)

        root.edit_product_mahsulot_nomi.setText(product?.name)
        root.edit_product_mahsulot_narxi.setText(product?.costDriver.toString())
        root.edit_product_mahsulot_mijoz_uchun_narx.setText(product?.costCustomer.toString())
        root.edit_product_mahsulot_astakasi.setText(product?.balance.toString())
        root.edit_product_mahsulot_karobkada_soni.setText(product?.totalBox.toString())

        root.edit_product_ok.setOnClickListener {
            var productName = root.edit_product_mahsulot_nomi.text.toString().trim()
            var productDrCost = root.edit_product_mahsulot_narxi.text.toString().trim()
            var productMijozCost =
                root.edit_product_mahsulot_mijoz_uchun_narx.text.toString().trim()
            var qolgan_astatka = root.edit_product_mahsulot_astakasi.text.toString().trim()
            var karobkada_soni = root.edit_product_mahsulot_karobkada_soni.text.toString().trim()
            if (productName != "" && productDrCost != "" && productMijozCost != "" && qolgan_astatka != "" && karobkada_soni != "") {
                val database = AppDatabase.get.getDatabase()
                val getMagnitDao = database.getProductDao()

                getMagnitDao?.updateProduct(
                    product?.supplierID!!,
                    productName,
                    Integer.parseInt(productMijozCost),
                    Integer.parseInt(productDrCost),
                    Integer.parseInt(karobkada_soni),
                    Integer.parseInt(qolgan_astatka),
                    product?.id!!
                )
                findNavController().popBackStack()
                container?.let {
                    Snackbar.make(
                        it.getChildAt(0),
                        "Mahsulot muvaffaqiyatli tahrirlandi",
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                }
            } else {
                container?.getChildAt(0)?.let {
                    Snackbar.make(it, "Barcha maydonlarni to'ldiring!", Snackbar.LENGTH_LONG)
                        .setAction("Ok", object : View.OnClickListener {
                            override fun onClick(v: View?) {

                            }

                        }).show()
                }
            }
        }

        root.edit_product_bekor_qilish.setOnClickListener {
            findNavController().popBackStack()
        }

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Product) =
            EditProductFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}