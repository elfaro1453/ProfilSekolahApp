package com.google.developers.profilsekolahapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.developers.profilsekolahapp.R

class SekolahFragment : Fragment() {

    // onCreateView diisi dengan kode identifikasi layout
    // sesuai dengan nama fungsinya, onCreateView hanya untuk
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sekolah, container,  false)
    }

}