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
import com.google.developers.profilsekolahapp.model.Prestasi
import com.google.developers.profilsekolahapp.recyclerview.PrestasiItemListAdapter
import com.google.developers.profilsekolahapp.retrofit.RetrofitInterfaces
import com.google.developers.profilsekolahapp.retrofit.RetrofitService
import com.google.developers.profilsekolahapp.room.RoomDB
import kotlinx.android.synthetic.main.fragment_prestasi.view.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class PrestasiFragment : Fragment() {

    private lateinit var adapterRv: PrestasiItemListAdapter
    private lateinit var loadingDialog: LoadingDialogFragment
    private lateinit var roomDB: RoomDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_prestasi, container, false)
        loadingDialog = LoadingDialogFragment(view.context, container!!)
        adapterRv = PrestasiItemListAdapter()
        view.rv_prestasi.setHasFixedSize(true)
        view.rv_prestasi.layoutManager = LinearLayoutManager(view.context)
        view.rv_prestasi.adapter = adapterRv

        roomDB = RoomDB.getInstance(view.context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog.startLoadingDialog()
        val handlerThread = CoroutineExceptionHandler { _, error ->
            Log.e("ErrorPrestasi", error.message.toString())
            Toast.makeText(
                context,
                "Tidak bisa menghubungi Server..\nSilahkan Periksa koneksi Internet",
                Toast.LENGTH_LONG
            ).show()
        }
        val prestasiItem = roomDB.roomDao().getPrestasi()
        prestasiItem.observe(viewLifecycleOwner, {
            Log.e("Banyak Data Prestasi", it.size.toString())
            if (it.isNotEmpty()) {
                val dataItem = arrayListOf<Prestasi>()
                it.forEach {
                    val item = Prestasi(
                        title = it.prestasi.title,
                        data = it.data
                    )
                    dataItem.add(item)
                }
                adapterRv.addData(dataItem)
                adapterRv.notifyDataSetChanged()
                loadingDialog.dismissDialog()
            } else {
                // pada fragment kita gunakan viewLifecyclerOwner untuk menjalankan fungsi suspend / asynchronous
                viewLifecycleOwner.lifecycleScope.launch(handlerThread) {
                    // buat variabel untuk membuat retrofitService
                    val retrofitService =
                        RetrofitService.buildService(RetrofitInterfaces::class.java)
                    // jalankan fungsi getDataGaleri yang berjalan secara asynchronous / di background
                    val request = retrofitService.getDataPrestasi()
                    if (request.isSuccessful) { // jika request sukses
                        val dataPrestasi = request.body() as List<Prestasi>
                        dataPrestasi.forEach { prestasi ->
                            val typeData = prestasi.title
                            prestasi.data.forEach { itemRv ->
                                itemRv.type = typeData
                            }
                            roomDB.roomDao().insertPrestasi(prestasi)
                            roomDB.roomDao().insertData(prestasi.data)
                        }
                    }
                }
            }
        })
    }
}