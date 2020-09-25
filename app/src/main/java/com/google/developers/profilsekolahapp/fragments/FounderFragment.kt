package com.google.developers.profilsekolahapp.fragments

import android.content.Intent
import android.net.Uri
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
import com.google.developers.profilsekolahapp.model.Founder
import com.google.developers.profilsekolahapp.retrofit.RetrofitInterfaces
import com.google.developers.profilsekolahapp.retrofit.RetrofitService
import com.google.developers.profilsekolahapp.room.RoomDB
import kotlinx.android.synthetic.main.founder_content.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class FounderFragment : Fragment() {

    private lateinit var loadingDialog: LoadingDialogFragment
    private lateinit var roomDB: RoomDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_founder, container, false)
        loadingDialog = LoadingDialogFragment(view.context, container!!)
        roomDB = RoomDB.getInstance(view.context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog.startLoadingDialog()
        val handlerThread = CoroutineExceptionHandler { _, error ->
            Toast.makeText(
                context,
                "Tidak bisa menghubungi Server..\nSilahkan Periksa koneksi Internet",
                Toast.LENGTH_LONG
            ).show()
        }
        val founder = roomDB.roomDao().getDataFounder()
        founder.observe(viewLifecycleOwner, {
            if (it == null) {
                // pada fragment kita gunakan viewLifecyclerOwner untuk menjalankan fungsi suspend / asynchronous
                viewLifecycleOwner.lifecycleScope.launch(handlerThread) {
                    val retrofitService =
                        RetrofitService.buildService(RetrofitInterfaces::class.java)
                    // jalankan fungsi getDataGaleri yang berjalan secara asynchronous / di background
                    val request = retrofitService.getDataFounder()
                    if (request.isSuccessful) { // jika request sukses
                        val dataFounder = request.body() as Founder
                        roomDB.roomDao().insertFounder(dataFounder)
                    }
                }
            } else {
                Glide.with(this@FounderFragment)
                    .load(it.image)
                    .circleCrop()
                    .into(img_view)
                txt_judul.text = it.name
                txt_email.text = it.email
                txt_latarbelakang.text = it.background

                btn_facebook.setOnClickListener { view ->
                    // Uri.parse untuk mengubah URL String ke bentuk Uri ( Uniform Resource Identifier (URI) )
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.facebook))
                    startActivity(intent)
                }
                loadingDialog.dismissDialog()
            }
        })
    }
}