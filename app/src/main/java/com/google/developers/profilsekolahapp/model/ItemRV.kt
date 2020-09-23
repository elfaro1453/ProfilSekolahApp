package com.google.developers.profilsekolahapp.model

/**
 * Created by Imam Fahrur Rofi on 16/09/2020.
 *
 * @Json berasal dari moshi
 */
data class ItemRV(
    var urlGambar: String = "",
    var title: String = "",
    var description: String = "",
    var type: String = ""
)

data class Prestasi(
    var title: String = "",
    var data: List<ItemRV> = arrayListOf()
)