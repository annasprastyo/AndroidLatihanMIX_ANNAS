package com.example.androidmix_annas.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidmix_annas.PrefsHelper
import com.example.androidmix_annas.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Item1 : Fragment() {

    private var uploadAdapter : UploadAdapter? = null
    private var rcView : RecyclerView? = null
    private var list : MutableList<UploadModel> = ArrayList<UploadModel>()
    lateinit var dbref : DatabaseReference
    lateinit var helperPrefs: PrefsHelper
    lateinit var fAuth : FirebaseAuth

    companion object {
        fun getInstance() : Item1 =
            Item1()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            // Inflate the layout for this fragment
            return inflater!!.inflate(R.layout.layout_item1_2, container, false)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        helperPrefs = PrefsHelper(activity!!)
        rcView = view.findViewById(R.id.rc_view)
        rcView!!.layoutManager = LinearLayoutManager(activity!!)
        rcView!!.setHasFixedSize(true)
        fAuth = FirebaseAuth.getInstance()
        val counterID = helperPrefs.getCounterId()

        dbref = FirebaseDatabase.getInstance().getReference("dataUploads")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                list = ArrayList<UploadModel>()
                for (dataSnapshot in p0.children){
                    val addDataAll = dataSnapshot.getValue(UploadModel::class.java)
                    addDataAll!!.setKey(dataSnapshot.key!!)
                    list.add(addDataAll!!)
                    uploadAdapter = UploadAdapter(activity!!, list)
                    rcView!!.adapter = uploadAdapter
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.e("TAG_ERROR", p0.message)
            }

        })
    }

}