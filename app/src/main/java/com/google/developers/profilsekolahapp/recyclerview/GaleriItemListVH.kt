package com.google.developers.profilsekolahapp.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.developers.profilsekolahapp.model.ItemRV
import kotlinx.android.synthetic.main.galeri_item_list.view.*

/**
 * Created by Imam Fahrur Rofi on 17/09/2020.
 */
class GaleriItemListVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: ItemRV) {
        itemView.run {
            txt_title.text = item.title
            txt_description.text = item.description
            Glide.with(this).load(item.urlGambar).into(img_item)
        }
    }
}