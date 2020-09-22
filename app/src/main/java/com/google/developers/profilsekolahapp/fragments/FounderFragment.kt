package com.google.developers.profilsekolahapp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.developers.profilsekolahapp.R
import com.google.developers.profilsekolahapp.model.Founder
import com.google.developers.profilsekolahapp.retrofit.RetrofitInterfaces
import com.google.developers.profilsekolahapp.retrofit.RetrofitService
import kotlinx.android.synthetic.main.founder_content.*
import kotlinx.coroutines.launch

class FounderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_founder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val retrofitService = RetrofitService.buildService(RetrofitInterfaces::class.java)
        // pada fragment kita gunakan viewLifecyclerOwner untuk menjalankan fungsi suspend / asynchronous
        viewLifecycleOwner.lifecycleScope.launch {
            // jalankan fungsi getDataGaleri yang berjalan secara asynchronous / di background
            val request = retrofitService.getDataFounder()
            if (request.isSuccessful) { // jika request sukses
                val dataFounder = request.body() as Founder
                Glide.with(this@FounderFragment)
                    .load(dataFounder.image)
                    .circleCrop()
                    .into(img_view)
                txt_judul.text = dataFounder.name
                txt_email.text = dataFounder.email
                txt_latarbelakang.text = dataFounder.background

                btn_facebook.setOnClickListener {
                    // Uri.parse untuk mengubah URL String ke bentuk Uri ( Uniform Resource Identifier (URI) )
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(dataFounder.facebook))
                    startActivity(intent)
                }
            }
        }
    }
}