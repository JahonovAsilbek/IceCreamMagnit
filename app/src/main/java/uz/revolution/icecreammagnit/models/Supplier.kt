package uz.revolution.icecreammagnit.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "supplier")
data class Supplier(

    @PrimaryKey val supplierID: Int,

    @ColumnInfo(name = "name") val name: String,

    )