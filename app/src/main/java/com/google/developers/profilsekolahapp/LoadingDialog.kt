package com.google.developers.profilsekolahapp

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by Imam Fahrur Rofi on 23/09/2020.
 */
class LoadingDialogFragment(context: Context, viewGroup: ViewGroup) {
    // inisialisasi view yang akan digunakan pada alertDialog
    private val inflater = LayoutInflater.from(context)
    private val viewLoadingBar = inflater.inflate(R.layout.loading_bar, viewGroup, false)

    // inisialisasi mContext sebagai variabel private yang bisa diakses oleh semua fungsi di dalam class
    private val mContext = context

    // buat variabel dialog tipe data AlertDialog
    private lateinit var dialog: AlertDialog

    // Buat fungsi startLoadingDialog untuk memulai loading dialog
    fun startLoadingDialog() {
        // viewLoadingBar.parent digunakan untuk mengecek apakah alertdialog sudah dibuat
        // logikanya adalah loading_bar.xml dimasukkan ke dalam alertDialog,
        // maka alertDialog dianggap sebagai parent dari loading_bar.xml / view
        // Jika parent (dalam hal ini alertDialog) belum ada, maka buat dulu.
        // Jika sudah ada tinggal tampilkan alertDialog tsb
        if (viewLoadingBar.parent == null) {
            val alertDialog = AlertDialog.Builder(mContext)
            alertDialog.setView(viewLoadingBar)
            alertDialog.setCancelable(false)
            dialog = alertDialog.create()
        }
        dialog.show()
    }

    // buat fungsi dismissDialog untuk menutup loading Dialog
    fun dismissDialog() {
        dialog.dismiss()
    }
}