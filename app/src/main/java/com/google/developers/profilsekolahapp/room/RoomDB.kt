package com.google.developers.profilsekolahapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.developers.profilsekolahapp.model.Founder
import com.google.developers.profilsekolahapp.model.ItemRV
import com.google.developers.profilsekolahapp.model.Prestasi

/**
 * Created by Imam Fahrur Rofi on 24/09/2020.
 */

@Database(
    entities = [ItemRV::class, Prestasi::class, Founder::class], version = 3, exportSchema = false
)
abstract class RoomDB : RoomDatabase() {
    abstract fun roomDao(): RoomDAO

    companion object {
        @Volatile
        private var instance: RoomDB? = null

        /**
         * Returns an instance of Room Database.
         *
         * @param context application context
         * @return The singleton LetterDatabase
         */
        fun getInstance(context: Context): RoomDB {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(context, RoomDB::class.java, "item.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
    }
}
