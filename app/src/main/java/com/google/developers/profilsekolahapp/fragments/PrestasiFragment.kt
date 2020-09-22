package com.google.developers.profilsekolahapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.developers.profilsekolahapp.R
import com.google.developers.profilsekolahapp.model.Prestasi
import com.google.developers.profilsekolahapp.recyclerview.PrestasiItemListAdapter
import com.google.developers.profilsekolahapp.retrofit.RetrofitInterfaces
import com.google.developers.profilsekolahapp.retrofit.RetrofitService
import kotlinx.android.synthetic.main.fragment_prestasi.view.*
import kotlinx.coroutines.launch

class PrestasiFragment : Fragment() {

    private lateinit var adapterRv: PrestasiItemListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_prestasi, container, false)
        adapterRv = PrestasiItemListAdapter()
        view.rv_prestasi.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(view.context)
            adapter = adapterRv
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // buat variabel untuk membuat retrofitService
        val retrofitService = RetrofitService.buildService(RetrofitInterfaces::class.java)
        // pada fragment kita gunakan viewLifecyclerOwner untuk menjalankan fungsi suspend / asynchronous
        viewLifecycleOwner.lifecycleScope.launch {
            // jalankan fungsi getDataGaleri yang berjalan secara asynchronous / di background
            val request = retrofitService.getDataPrestasi()
            if (request.isSuccessful) { // jika request sukses
                val dataPrestasi = request.body() as List<Prestasi>
                adapterRv.addData(dataPrestasi)
                adapterRv.notifyDataSetChanged()
            }
        }
    }
}