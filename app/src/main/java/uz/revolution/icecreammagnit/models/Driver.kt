package uz.revolution.icecreammagnit.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "driver")
class Driver : Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var driverID: Int = 0

    @ColumnInfo(name = "serial_number")
    var serialNumber: Int = 0

    @ColumnInfo(name = "date")
    var date: String? = null

    @ColumnInfo(name = "product")
    var product: String? = null

    @ColumnInfo(name = "total_box")
    var totalBox: Int = 0

    @ColumnInfo(name = "given_cash")
    var givenCash: Int = 0

    @ColumnInfo(name = "received_cash")
    var receivedCash: Int = 0

    constructor()

    @Ignore
    constructor(
        serialNumber: Int,
        date: String,
        product: String,
        totalBox: Int,
        givenCash: Int,
        receivedCash: Int
    ) {
        this.serialNumber = serialNumber
        this.date = date
        this.product = product
        this.totalBox = totalBox
        this.givenCash = givenCash
        this.receivedCash = receivedCash
    }
}