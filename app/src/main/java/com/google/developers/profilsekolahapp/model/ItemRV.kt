package com.google.developers.profilsekolahapp.model

import com.squareup.moshi.Json

/**
 * Created by Imam Fahrur Rofi on 16/09/2020.
 *
 * @Json berasal dari moshi
 */
data class ItemRV(
    @Json(name = "urlGambar")
    var urlGambar: String = "",
    @Json(name = "title")
    var title: String = "",
    @Json(name = "description")
    var description: String = "",
    var type: String = ""
)

data class Prestasi(
    @Json(name = "title")
    var title: String = "",
    @Json(name = "data")
    var data: List<ItemRV> = arrayListOf()
)