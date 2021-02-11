package uz.revolution.icecreammagnit.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "driver")
data class Driver(

    @PrimaryKey val driverID: Int,

    @ColumnInfo(name = "serial_number") val serialNumber: Int,

    @ColumnInfo(name = "date") val date: String?,

    @ColumnInfo(name = "product") val product: String?,

    @ColumnInfo(name = "total_box") val totalBox: Int,

    @ColumnInfo(name = "given_cash") val givenCash: Int,

    @ColumnInfo(name = "received_cash") val receivedCash: Int
)