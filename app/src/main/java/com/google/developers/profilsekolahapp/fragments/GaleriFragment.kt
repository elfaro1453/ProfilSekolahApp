package com.google.developers.profilsekolahapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.developers.profilsekolahapp.LoadingDialogFragment
import com.google.developers.profilsekolahapp.R
import com.google.developers.profilsekolahapp.model.ItemRV
import com.google.developers.profilsekolahapp.recyclerview.GaleriItemListAdapter
import com.google.developers.profilsekolahapp.retrofit.RetrofitInterfaces
import com.google.developers.profilsekolahapp.retrofit.RetrofitService
import com.google.developers.profilsekolahapp.room.RoomDB
import kotlinx.android.synthetic.main.fragment_galeri.view.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class GaleriFragment : Fragment() {

    private lateinit var adapter: GaleriItemListAdapter
    private lateinit var loadingDialog: LoadingDialogFragment
    private lateinit var roomDB: RoomDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate view yang akan digunakan, dalam hal ini fragment_galeri
        val view = inflater.inflate(R.layout.fragment_galeri, container, false)
        loadingDialog = LoadingDialogFragment(view.context, container!!)
        roomDB = RoomDB.getInstance(view.context)
        // buat adapter untuk recyclerview
        adapter = GaleriItemListAdapter({
            viewLifecycleOwner.lifecycleScope.launch {
                roomDB.roomDao().removeData(it)
            }
        })
        // atur recyclerview rv_galeri
        view.rv_galeri.adapter = adapter
        view.rv_galeri.setHasFixedSize(true)
        view.rv_galeri.layoutManager = LinearLayoutManager(view.context)
        // pengaturan recyclerview selesai
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
        // pada fragment kita gunakan viewLifecyclerOwner untuk menjalankan fungsi suspend / asynchronous
        viewLifecycleOwner.lifecycleScope.launch(handlerThread) {
            // buat variabel untuk membuat retrofitService
            val retrofitService = RetrofitService.buildService(RetrofitInterfaces::class.java)
            // jalankan fungsi getDataGaleri yang berjalan secara asynchronous / di background
            val request = retrofitService.getDataGaleri()
            if (request.isSuccessful) { // jika request sukses
                val dataGaleri = request.body() as List<ItemRV>
                adapter.addData(dataGaleri)
                adapter.notifyDataSetChanged()
                loadingDialog.dismissDialog()
            }
        }
    }
}