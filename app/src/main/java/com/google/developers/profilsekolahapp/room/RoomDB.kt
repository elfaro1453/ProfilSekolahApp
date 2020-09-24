package com.google.developers.profilsekolahapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.developers.profilsekolahapp.model.ItemRV

/**
 * Created by Imam Fahrur Rofi on 24/09/2020.
 */
@Database(entities = arrayOf(ItemRV::class), version = 1)
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
                    .build()
            }
        }
    }
}
