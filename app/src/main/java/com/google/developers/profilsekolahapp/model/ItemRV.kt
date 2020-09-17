package com.google.developers.profilsekolahapp.model

import com.squareup.moshi.Json

/**
 * Created by Imam Fahrur Rofi on 16/09/2020.
 */
data class ItemRV(
    @Json(name = "urlGambar")
    var urlGambar: String = "",
    @Json(name = "title")
    var title: String = "",
    @Json(name = "deskripsi")
    var deskripsi: String = "",
    var type: String = ""
)