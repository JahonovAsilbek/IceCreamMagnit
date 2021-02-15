package uz.revolution.icecreammagnit.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "magnit_temporary")
class MagnitTemporary :Serializable{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "received_number")
    var receivedNumber:Int=0

    constructor()

    @Ignore
    constructor(name: String, receivedNumber: Int){
        this.name=name
        this.receivedNumber=receivedNumber
    }
    /*
    *
    * id
    * name
    * olingan soni
    *
    *
    *
     */
}