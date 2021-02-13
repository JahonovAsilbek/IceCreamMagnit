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

    @Query("UPDATE products SET supplier_id=:supplierID, name=:name, cost_customer=:costCustomer,cost_driver=:costDriver,received_cost=:receivedCost,total_box=:totalBox, balance=:balance WHERE id=:id")
    fun updateProduct(
        supplierID: Int,
        name: String,
        costCustomer: Int,
        costDriver: Int,
        receivedCost: Int,
        totalBox: Int,
        balance: Int,
        id: Int
    )

    @Query("UPDATE products SET balance=:balance+balance WHERE id=:id")
    fun addBalance(balance: Int, id: Int)

    @Delete
    fun deleteProduct(product: Product)

    @Query("SELECT * FROM products")
    fun getProductList(): List<Product>

    @Query("SELECT * FROM products WHERE supplier_id=:supplierID")
    fun getProductBySupplierID(supplierID: Int): List<Product>

    @Query("SELECT * FROM products WHERE id=:id")
    fun getProductByID(id: Int): Product

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

    @Query("UPDATE driver SET serial_number=:serialNumber, date=:date, product=:product, total_box=:totalBox, given_cash=:givenCash, received_cash=:receivedCash WHERE id=:driverID")
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

    @Query("UPDATE supplier SET name=:name WHERE supplier_id=:supplierID")
    fun updateSupplier(name: String, supplierID: Int)

    @Query("SELECT * FROM supplier WHERE supplier_id=:id")
    fun getSupplierByID(id: Int): Supplier

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
    fun getAllTemporary(): List<Temporary>

    @Delete
    fun deleteTemporary(temporary: Temporary)

    @Query("DELETE FROM `temporary`")
    fun deleteAllTemporary()

    /*
   *
   *
   *
   *
   *
   *
                       CustomerTemporary Dao Methods
   *
   *
   *
   *
   *
   *
   */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomerTemporary(customerTemporary: CustomerTemporary)

    @Query("SELECT * FROM customer_temporary")
    fun getAllCustomerTemporary(): List<CustomerTemporary>

    @Query("SELECT * FROM customer_temporary WHERE customer_id=:customerID")
    fun getCustomerTemporaryByCustomerID(customerID: Int): List<CustomerTemporary>

    /*
   *
   *
   *
   *
   *
   *
                       MagnitTemporary Dao Methods
   *
   *
   *
   *
   *
   *
   */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMagnitTemporary(magnitTemporary: MagnitTemporary)

    @Query("SELECT * FROM magnit_temporary")
    fun getAllMagnitTemporary():List<MagnitTemporary>

}