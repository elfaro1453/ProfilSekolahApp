package com.google.developers.profilsekolahapp.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.developers.profilsekolahapp.R
import com.google.developers.profilsekolahapp.model.ItemRV

/**
 * Created by Imam Fahrur Rofi on 17/09/2020.
 */
class GaleriItemListAdapter : RecyclerView.Adapter<GaleriItemListVH>() {
    private var listItem = arrayListOf<ItemRV>()

    fun addData(items: List<ItemRV>) {
        listItem.clear()
        listItem.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GaleriItemListVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.galeri_item_list, parent, false)
        return GaleriItemListVH(view)
    }

    override fun onBindViewHolder(holder: GaleriItemListVH, position: Int) {
        val data = listItem[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listItem.size
}