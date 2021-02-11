package uz.revolution.icecreammagnit.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "magnit")
data class Magnit(

    @PrimaryKey val id: Int,

    @ColumnInfo(name = "date") val date: String?,

    @ColumnInfo(name = "product") val product: String?,

    @ColumnInfo(name = "total_box") val totalBox: Int
)