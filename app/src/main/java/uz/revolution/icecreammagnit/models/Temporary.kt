package uz.revolution.icecreammagnit.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "temporary")
data class Temporary(

    @PrimaryKey val id: Int,

    @ColumnInfo(name = "name") val name: String?,

    @ColumnInfo(name = "driver_id") val driverID: Int,

    @ColumnInfo(name = "number_received") val numberReceived: Int,

    @ColumnInfo(name = "total_box") val totalBox: Int,

    @ColumnInfo(name = "cost") val cost: Int

)