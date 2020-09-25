package com.google.developers.profilsekolahapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.google.developers.profilsekolahapp.model.Founder
import com.google.developers.profilsekolahapp.model.ItemRV
import com.google.developers.profilsekolahapp.model.Prestasi
import com.google.developers.profilsekolahapp.model.PrestasiItem

/**
 * Created by Imam Fahrur Rofi on 24/09/2020.
 */
@Dao
interface RoomDAO {

    @Query("SELECT * FROM item WHERE type = :type")
    fun getDataByType(type: String): LiveData<List<ItemRV>>

    @Query("DELETE FROM item")
    suspend fun resetDatabase()

    @Query("DELETE FROM item WHERE type = :type")
    suspend fun resetType(type: String)

    @Insert
    suspend fun insertData(data: List<ItemRV>)

    @Transaction
    @Query("SELECT * FROM prestasi")
    fun getPrestasi(): LiveData<List<PrestasiItem>>

    @Insert
    suspend fun insertPrestasi(data: Prestasi)

    @Insert
    suspend fun insertFounder(data: Founder)

    @Query("SELECT * FROM founder ORDER BY id DESC LIMIT 1")
    fun getDataFounder(): LiveData<Founder>

    @Query("DELETE FROM founder")
    suspend fun resetFounder()
}
