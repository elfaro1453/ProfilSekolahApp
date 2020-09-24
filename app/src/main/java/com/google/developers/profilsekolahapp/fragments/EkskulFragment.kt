package com.google.developers.profilsekolahapp.fragments

import android.os.Bundle
import android.util.Log
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
import kotlinx.android.synthetic.main.fragment_ekskul.view.*
import kotlinx.coroutines.launch

class EkskulFragment : Fragment() {

    // adapter recylerview perlu jadi global variabel sehingga bisa diakses oleh semua fungsi di dalam class
    private lateinit var adapterRv: GaleriItemListAdapter
    private lateinit var loadingDialog: LoadingDialogFragment

    private lateinit var roomDB: RoomDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ekskul, container, false)
        // inisialisasi loadingDialog menggunakan context dari view dan container
        loadingDialog = LoadingDialogFragment(view.context, container!!)

        roomDB = RoomDB.getInstance(view.context)

        // definisikan dulu adapternya
        adapterRv = GaleriItemListAdapter()
        // modifikasi bagian recylerview yang ada di fragment_ekskul
        view.rv_ekskul.setHasFixedSize(true)
        view.rv_ekskul.layoutManager = LinearLayoutManager(view.context)
        view.rv_ekskul.adapter = adapterRv
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog.startLoadingDialog()
        val dataItem = roomDB.roomDao().getDataByType("ekskul")
        dataItem.observe(viewLifecycleOwner, {
            Log.e("banyak data", it.size.toString())
            if (it.isNotEmpty()) {
                adapterRv.addData(it)
                adapterRv.notifyDataSetChanged()
                loadingDialog.dismissDialog()
            } else {
                Toast.makeText(context, "Database Kosong", Toast.LENGTH_SHORT).show()
                // buat variabel untuk membuat retrofitService
                val retrofitService = RetrofitService.buildService(RetrofitInterfaces::class.java)
                // pada fragment kita gunakan viewLifecyclerOwner untuk menjalankan fungsi suspend / asynchronous
                viewLifecycleOwner.lifecycleScope.launch {
                    // jalankan fungsi getDataGaleri yang berjalan secara asynchronous / di background
                    val request = retrofitService.getDataEkskul()
                    if (request.isSuccessful) { // jika request sukses
                        val dataEkskul = request.body() as List<ItemRV>
                        dataEkskul.forEach {
                            it.type = "ekskul"
                        }
                        roomDB.roomDao().insertData(dataEkskul)
                    }
                }
            }
        })
    }
}

