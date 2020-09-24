package com.google.developers.profilsekolahapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.google.developers.profilsekolahapp.model.ItemRV

/**
 * Created by Imam Fahrur Rofi on 24/09/2020.
 */
@Dao
interface RoomDAO {

    @Query("SELECT * FROM item WHERE type = :type")
    fun getDataByType(type: String): LiveData<List<ItemRV>>

//    @Query("SELECT * FROM item WHERE id = :id")
//    fun getData(id: Long): LiveData<ItemRV>

    @Query("DELETE FROM item")
    suspend fun resetDatabase()

    @Query("DELETE FROM item WHERE type = :type")
    suspend fun resetType(type: String)

    @Insert
    suspend fun insertData(data: List<ItemRV>)

//    @Delete
//    fun delete(data: ItemRV)
}
