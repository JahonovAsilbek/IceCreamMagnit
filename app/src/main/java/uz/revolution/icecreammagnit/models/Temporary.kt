package uz.revolution.icecreammagnit.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "temporary")
class Temporary : Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "driver_id")
    var driverID: Int = 0

    @ColumnInfo(name = "number_received")
    var numberReceived: Int = 0

    @ColumnInfo(name = "total_box")
    var totalBox: Int = 0

    @ColumnInfo(name = "cost")
    var cost: Int = 0

    constructor()

    @Ignore
    constructor(name: String, driverID: Int, numberReceived: Int, totalBox: Int, cost: Int) {
        this.name = name
        this.driverID = driverID
        this.numberReceived = numberReceived
        this.totalBox = totalBox
        this.cost = cost
    }

}