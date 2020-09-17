package com.google.developers.profilsekolahapp.retrofit

import com.google.developers.profilsekolahapp.model.ItemRV
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Imam Fahrur Rofi on 17/09/2020.
 *
 * RetrofitInterfaces digunakan untuk menentukan alamat lengkap dari url
 */
interface RetrofitInterfaces {

    // suspend fun digunakan untuk membuat fungsi yang berjalan Asynchronous

    @GET("data/ekskul.json")
    suspend fun getDataEkskul(): Response<List<ItemRV>>

    @GET("data/galeri.json")
    suspend fun getDataGaleri(): Response<List<ItemRV>>
}