package uz.revolution.icecreammagnit.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "received_products")
data class ReceivedProducts(

    @PrimaryKey val id: Int,

    @ColumnInfo(name = "supplier_id") val supplierID: Int,

    @ColumnInfo(name = "product") val product: String?,

    @ColumnInfo(name = "total_box") val totalBox: Int,

    @ColumnInfo(name = "total_cash") val totalCash: Int,

    @ColumnInfo(name = "received_cash") val receivedCash: Int
)