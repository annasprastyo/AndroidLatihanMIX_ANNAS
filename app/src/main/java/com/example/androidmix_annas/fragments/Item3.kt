package com.example.androidmix_annas.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.androidmix_annas.R

class Item3 : Fragment() {
    companion object {
        fun getInstance() : Item3 =
            Item3()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.layout_item3, container, false)
    }
}
