package uz.revolution.icecreammagnit.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "supplier")
class Supplier : Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "supplier_id")
    var supplierID: Int = 0

    @ColumnInfo(name = "name")
    var name: String? = null

    constructor()

    @Ignore
    constructor(name: String) {
        this.name = name
    }
}