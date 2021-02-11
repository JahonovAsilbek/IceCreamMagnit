package uz.revolution.icecreammagnit.daos

import androidx.room.*
import uz.revolution.icecreammagnit.models.*

@Dao
interface MagnitDao {

    /*
    *
    *
    *
    *
    Product Dao Methods
    *
    *
    *
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: Product)

    @Query("UPDATE products SET supplier_id=:supplierID, name=:name, cost_customer=:costCustomer,cost_driver=:costDriver,total_box=:totalBox, balance=:balance WHERE id=:id")
    fun updateProduct(
        supplierID: Int,
        name: String,
        costCustomer: Int,
        costDriver: Int,
        totalBox: Int,
        balance: Int,
        id: Int
    )

    @Delete
    fun deleteProduct(product: Product)

    @Query("SELECT * FROM products")
    fun getProductList(): List<Product>

    @Query("SELECT * FROM products WHERE supplier_id=:supplierID")
    fun getProductBySupplierID(supplierID: Int): List<Product>

    /*
    *
    *
    *
    *
    *
    *
                    Driver Dao Methods
   *
   *
   *
   *
   *
   *
    */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDriver(driver: Driver)

    @Query("UPDATE driver SET serial_number=:serialNumber, date=:date, product=:product, total_box=:totalBox, given_cash=:givenCash, received_cash=:receivedCash WHERE driverID=:driverID")
    fun updateDriver(
        serialNumber: Int,
        date: String,
        product: String,
        totalBox: Int,
        givenCash: Int,
        receivedCash: Int,
        driverID: Int

    )

    @Query("SELECT * FROM driver")
    fun getAllDriver(): List<Driver>

    @Query("SELECT * FROM driver WHERE serial_number=:serialNumber")
    fun getDriverBySerialNumber(serialNumber: Int): List<Driver>

    @Delete
    fun deleteDriver(driver: Driver)

    /*
    *
    *
    *
    *
    *
    *
                 Customer Dao Methods
   *
   *
   *
   *
   *
   */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomer(customer: Customer)

    @Query("SELECT * FROM customer")
    fun getAllCustomer(): List<Customer>

    @Query("SELECT * FROM customer WHERE serial_number=:serialNumber")
    fun getCustomerBySerialNumber(serialNumber: Int): List<Customer>

    @Query("UPDATE customer SET serial_number=:serialNumber,date=:date,product=:product,total_box=:totalBox,given_cash=:givenCash,received_cash=:receivedCash,id=:customerID")
    fun updateCustomer(
        serialNumber: Int,
        date: String,
        product: String,
        totalBox: Int,
        givenCash: Int,
        receivedCash: Int,
        customerID: Int
    )

    @Delete
    fun deleteCustomer(customer: Customer)

    /*
    *
    *
    *
    *
    *
    *
                        Suppliers Dao Methods
    *
    *
    *
    *
    *
    *
    */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSupplier(supplier: Supplier)

    @Query("SELECT * FROM supplier")
    fun getAllSuppliers(): List<Supplier>

    @Query("UPDATE supplier SET name=:name WHERE supplierID=:supplierID")
    fun updateSupplier(name: String, supplierID: Int)

    /*
    *
    *
    *
    *
    *
    *
                        Received Products Dao Methods
    *
    *
    *
    *
    *
    *
    */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReceivedProducts(receivedProducts: ReceivedProducts)

    @Query("SELECT * FROM received_products")
    fun getAllReceivedProducts(): List<ReceivedProducts>

    @Query("SELECT * FROM received_products WHERE supplier_id=:supplierID")
    fun getReceivedProductsBySupplierID(supplierID: Int): List<ReceivedProducts>

    @Delete
    fun deleteReceivedProduct(receivedProducts: ReceivedProducts)

    /*
    *
    *
    *
    *
    *
    *
                        For Magnit Dao Methods
    *
    *
    *
    *
    *
    *
    */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMagnit(magnit: Magnit)

    //gets all data from Magnit

    @Query("SELECT * FROM magnit")
    fun getAllProductsMagnit(): List<Magnit>

    // deletes data from magnit table

    @Delete
    fun deleteProductFromMagnit(magnit: Magnit)

    /*
    *
    *
    *
    *
    *
    *
                        Temporary Dao Methods
    *
    *
    *
    *
    *
    *
    */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTemporary(temporary: Temporary)

    @Query("SELECT * FROM `temporary`")
    fun getAllTemporary():List<Temporary>

    @Delete
    fun deleteTemporary(temporary: Temporary)

    @Query("DELETE FROM `temporary`")
    fun deleteAllTemporary()

}