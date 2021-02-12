package uz.revolution.icecreammagnit.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "received_products")
class ReceivedProducts : Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "supplier_id")
    var supplierID: Int = 0

    @ColumnInfo(name = "date")
    var date: String? = null

    @ColumnInfo(name = "product")
    var product: String? = null

    @ColumnInfo(name = "total_box")
    var totalBox: Int = 0

    @ColumnInfo(name = "total_cash")
    var totalCash: Int = 0

    @ColumnInfo(name = "received_cash")
    var receivedCash: Int = 0

    constructor()

    @Ignore
    constructor(
        supplierID: Int,
        date: String,
        product: String,
        totalBox: Int,
        totalCash: Int,
        receivedCash: Int
    ) {
        this.supplierID = supplierID
        this.date = date
        this.product = product
        this.totalBox = totalBox
        this.totalCash = totalCash
        this.receivedCash = receivedCash
    }
}