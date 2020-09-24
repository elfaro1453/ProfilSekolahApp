package com.google.developers.profilsekolahapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.developers.profilsekolahapp.LoadingDialogFragment
import com.google.developers.profilsekolahapp.R
import com.google.developers.profilsekolahapp.model.Sekolah
import com.google.developers.profilsekolahapp.retrofit.RetrofitInterfaces
import com.google.developers.profilsekolahapp.retrofit.RetrofitService
import kotlinx.android.synthetic.main.fragment_sekolah.*
import kotlinx.android.synthetic.main.sekolah_content.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class SekolahFragment : Fragment() {

    private lateinit var dialog: LoadingDialogFragment

    // onCreateView diisi dengan kode identifikasi layout
    // sesuai dengan nama fungsinya, onCreateView hanya untuk
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sekolah, container, false)
        dialog = LoadingDialogFragment(view.context, container!!)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog.startLoadingDialog()
        val handlerThread = CoroutineExceptionHandler { _, error ->
            Toast.makeText(
                context,
                "Tidak bisa menghubungi Server..\nSilahkan Periksa koneksi Internet",
                Toast.LENGTH_LONG
            ).show()
        }
        viewLifecycleOwner.lifecycleScope.launch(handlerThread) {
            val retrofitService = RetrofitService.buildService(RetrofitInterfaces::class.java)
            // jalankan fungsi getDataGaleri yang berjalan secara asynchronous / di background
            val request = retrofitService.getDataSekolah()
            if (request.isSuccessful) { // jika request sukses
                val dataSekolah = request.body() as Sekolah
                Glide.with(this@SekolahFragment)
                    .load(dataSekolah.image)
                    .into(img_school)
                sekolah_title.text = dataSekolah.title
                txt_alamat.text = dataSekolah.address
                txt_npsn.text = dataSekolah.npsn
                txt_telepon.text = dataSekolah.phone
                txt_website.text = dataSekolah.website
                dialog.dismissDialog()
            }
        }
    }
}