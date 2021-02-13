package uz.revolution.icecreammagnit.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "customer_temporary")
class CustomerTemporary : Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "customer_id")
    var customerID: Int = 0

    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "productCost")
    var productCost: Int = 0

    @ColumnInfo(name = "cost")
    var customerCost:Int=0

    @ColumnInfo(name = "box")
    var box: Int = 0

    @ColumnInfo(name = "karobkada_soni")
    var karobkadaSoni: Int = 0

    constructor()

    @Ignore
    constructor(customerID:Int,name:String,productCost:Int,customerCost:Int,box:Int,karobkadaSoni:Int){
        this.customerID=customerID
        this.name=name
        this.productCost=productCost
        this.customerCost=customerCost
        this.box=box
        this.karobkadaSoni=karobkadaSoni
    }

    /*

    id
    customer_id
    name
    mahsulot summasi
    karobka soni
    karobkada soni
    narxi(cusoomer cost)

     */
}