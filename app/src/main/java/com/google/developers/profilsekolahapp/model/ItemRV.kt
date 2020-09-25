package com.google.developers.profilsekolahapp.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

/**
 * Created by Imam Fahrur Rofi on 16/09/2020.
 *
 * @Json berasal dari moshi
 *
 * @Entity digunakan untuk membuat tabel pada android Room
 */
@Entity(tableName = "item")
data class ItemRV(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @Json(name = "urlGambar")
    var urlGambar: String = "",
    @Json(name = "title")
    var title: String = "",
    @Json(name = "description")
    var description: String = "",
    var type: String = ""
)

@Entity(tableName = "prestasi")
data class Prestasi(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @Json(name = "title")
    var title: String = "",
    @Json(name = "data")
    @Ignore
    var data: List<ItemRV> = arrayListOf()
)