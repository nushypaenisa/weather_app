package com.example.weatherapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapp.models.WeatherEntity
import com.example.weatherapp.models.WeatherHourlyData
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(entities = [WeatherEntity::class, WeatherHourlyData::class], version = 2)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao


    companion object {
        private var INSTANCE: WeatherDatabase? = null

        fun getDatabase(context: Context, passphrase: String): WeatherDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    // Create the encrypted database
                    val passphraseBytes = SQLiteDatabase.getBytes(passphrase.toCharArray())
                    val factory = SupportFactory(passphraseBytes)

                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        WeatherDatabase::class.java,
                        "weather.db"
                    )
                        .openHelperFactory(factory)
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}