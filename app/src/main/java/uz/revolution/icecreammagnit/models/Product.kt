package uz.revolution.icecreammagnit.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "products")
data class Product(

    @PrimaryKey val id: Int,

    @ColumnInfo(name = "supplier_id") val supplierID: Int,

    @ColumnInfo(name = "name") val name: String?,

    @ColumnInfo(name = "cost_customer") val costCustomer: Int,

    @ColumnInfo(name = "cost_driver") val costDriver: Int,

    @ColumnInfo(name = "total_box") val totalBox: Int,

    @ColumnInfo(name = "balance") val balance: Int

):Serializable