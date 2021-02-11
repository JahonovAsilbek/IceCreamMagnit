package uz.revolution.icecreammagnit.app

import android.app.Application
import uz.revolution.icecreammagnit.database.AppDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.initDatabase(this)
    }
}