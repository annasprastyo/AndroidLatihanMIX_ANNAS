package com.example.androidmix_annas.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidmix_annas.R

class Item1 : Fragment() {

    companion object {
        fun getInstance() : Item1 =
            Item1()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            // Inflate the layout for this fragment
            return inflater!!.inflate(R.layout.layout_item1, container, false)
        }

}