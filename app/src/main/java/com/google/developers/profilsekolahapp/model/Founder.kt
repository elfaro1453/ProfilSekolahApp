package com.google.developers.profilsekolahapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

/**
 * Created by Imam Fahrur Rofi on 22/09/2020.
 */
@Entity(tableName = "founder")
data class Founder(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @Json(name = "background")
    var background: String = "",
    @Json(name = "email")
    var email: String = "",
    @Json(name = "facebook")
    var facebook: String = "",
    @Json(name = "image")
    var image: String = "",
    @Json(name = "instagram")
    var instagram: String = "",
    @Json(name = "name")
    var name: String = "",
    @Json(name = "twitter")
    var twitter: String = ""
)