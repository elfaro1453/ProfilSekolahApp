package com.google.developers.profilsekolahapp.recyclerview

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.developers.profilsekolahapp.model.Prestasi
import kotlinx.android.synthetic.main.prestasi_item_list.view.*

/**
 * Created by Imam Fahrur Rofi on 22/09/2020.
 */
class PrestasiItemListVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindData(data: Prestasi) {
        val adapterRv = GaleriItemListAdapter()
        itemView.run {
            txt_judul.text = data.title
            rv_prestasi.setHasFixedSize(true)
            rv_prestasi.layoutManager = LinearLayoutManager(itemView.context)
            rv_prestasi.adapter = adapterRv
        }
        adapterRv.addData(data.data)
        adapterRv.notifyDataSetChanged()
    }

}