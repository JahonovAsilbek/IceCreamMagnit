package uz.revolution.icecreammagnit.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "products")
class Product : Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "supplier_id")
    var supplierID: Int = 0

    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "cost_customer")
    var costCustomer: Int = 0

    @ColumnInfo(name = "cost_driver")
    var costDriver: Int = 0

    @ColumnInfo(name = "received_cost")
    var receivedCost:Int=0

    @ColumnInfo(name = "total_box")
    var totalBox: Int = 0

    @ColumnInfo(name = "balance")
    var balance: Int = 0

    constructor()

    @Ignore
    constructor(
        supplierID: Int,
        name: String,
        costCustomer: Int,
        costDriver: Int,
        receivedCost:Int,
        totalBox: Int,
        balance: Int
    ) {
        this.supplierID = supplierID
        this.name = name
        this.costCustomer = costCustomer
        this.costDriver = costDriver
        this.receivedCost=receivedCost
        this.totalBox = totalBox
        this.balance = balance
    }

}