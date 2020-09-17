package com.google.developers.profilsekolahapp.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Imam Fahrur Rofi on 17/09/2020.
 */
object RetrofitService {
    // fungsi HttpLoggingInterceptor adalah mengecek status response dari server
    // keterangan status server di LogCat :
    // 200 = respose sukses
    // 404 = url not found
    // 401 = tidak ada otorisasi / API Key belum dimasukkan
    // 500 = Masalah di server
//    private fun interceptor(): Interceptor {
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BASIC
//        return interceptor
//    }

    // client bertugas sebagai alat penghubung ke server
    // di sini tempat kita memasukkan Interceptor
    private val client = OkHttpClient.Builder()
//        .addInterceptor(interceptor())
        .build()

    // retrofit bertugas sebagai pengatur client
    private val retrofit = Retrofit.Builder()
        // masukkan baseUrl sesuai alamat server
        .baseUrl("https://profil-sekolah-5bf9b.firebaseio.com/")
        // masukkan converter JSON yang digunakan (Moshi)
        .addConverterFactory(MoshiConverterFactory.create())
        // masukkan client yang digunakan
        .client(client)
        .build()

    // buildService digunakan untuk membuat service sesuai interface retrofit
    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}
