package uz.revolution.icecreammagnit.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.revolution.icecreammagnit.daos.MagnitDao
import uz.revolution.icecreammagnit.models.*

@Database(
    entities = [Product::class, Driver::class, Customer::class, Supplier::class, ReceivedProducts::class, Magnit::class,Temporary::class,CustomerTemporary::class,MagnitTemporary::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getProductDao(): MagnitDao?

    companion object {

        @Volatile
        private var database: AppDatabase? = null

        fun initDatabase(context: Context) {
            synchronized(this) {
                if (database == null) {
                    database = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "data.db"
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }
        }
    }

    object get {
        fun getDatabase(): AppDatabase {
            return database!!
        }
    }
}