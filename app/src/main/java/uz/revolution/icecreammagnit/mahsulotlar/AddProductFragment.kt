package uz.revolution.icecreammagnit.mahsulotlar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add_product.view.*
import uz.revolution.icecreammagnit.R
import uz.revolution.icecreammagnit.database.AppDatabase
import uz.revolution.icecreammagnit.models.Product

class AddProductFragment : Fragment() {

    lateinit var root: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_add_product, container, false)

        root.add_product_ok.setOnClickListener { v ->
            var productName = root.add_product_mahsulot_nomi.text.toString().trim()
            var productDrCost = root.add_product_mahsulot_narxi.text.toString().trim()
            var productMijozCost = root.add_product_mahsulot_mijoz_uchun_narx.text.toString().trim()
            var qolgan_astatka = root.add_product_mahsulot_astakasi.text.toString().trim()
            var karobkada_soni = root.add_product_mahsulot_karobkada_soni.text.toString().trim()
            var taminotchi=root.add_product_spinner.selectedItem.toString()
            if (productName != "" && productDrCost != "" && productMijozCost != "" && qolgan_astatka != "" && karobkada_soni != "") {
                val database = AppDatabase.get.getDatabase()
                val getMagnitDao = database.getProductDao()

                getMagnitDao?.insertProduct(
                    Product(
                        getMagnitDao.getProductList().size+1,
                        2,
                        //qilinadigan ishi bor
                        productName,
                        Integer.parseInt(productMijozCost),
                        Integer.parseInt(productDrCost),
                        Integer.parseInt(karobkada_soni),
                        Integer.parseInt(qolgan_astatka)

                    )
                )
                findNavController().popBackStack()
                container?.let {
                    Snackbar.make(it.getChildAt(0), "Mahsulot muvaffaqiyatli qo'shildi", Snackbar.LENGTH_SHORT)
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
        root.add_product_bekor_qilish.setOnClickListener { v ->
            findNavController().popBackStack()
        }
        return root
    }


}