package com.example.androidmix_annas.fragments

import android.app.AlertDialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.example.androidmix_annas.R

class UploadAdapter : RecyclerView.Adapter<UploadAdapter.UploadViewHolder> {
    lateinit var mContext : Context
    lateinit var itemUpload : List<UploadModel>
//    lateinit var listener : FirebaseDataListener

    constructor(){}
    constructor(mContext : Context, list: List<UploadModel>){
        this.mContext = mContext
        this.itemUpload = list
//        listener = mContext as Item1
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): UploadViewHolder {
        val view : View = LayoutInflater.from(p0.context).inflate(
            R.layout.layout_item1, p0, false)
        val uploadViewHolder = UploadViewHolder(view)
        return uploadViewHolder
    }
    override fun getItemCount(): Int {
        return itemUpload.size
    }

    override fun onBindViewHolder(p0: UploadViewHolder, p1: Int) {
        val uploadModel : UploadModel = itemUpload.get(p1)
        p0.et_nama.text = uploadModel.getUploadBy()
        p0.et_description.text = uploadModel.getDescUpload()
        Glide.with(mContext)
            .load(uploadModel.getFileUrl())
            .error(R.drawable.ic_launcher_background)
            .into(p0.image_upload)
        p0.ll_content.setOnClickListener {
            Toast.makeText(mContext, "contoh touch listener",
                Toast.LENGTH_SHORT).show()
        }
//        p0.ll_content.setOnLongClickListener(object : View.OnLongClickListener{
//            override fun onLongClick(v: View?): Boolean {
//                val builder = AlertDialog.Builder(mContext)
//                builder.setMessage("Pilih Operasi Data !!")
//                builder.setPositiveButton("Update"){
//                        dialog, which ->
//                    //                        Toast.makeText(mContext, "haloo saya update",
////                            Toast.LENGTH_SHORT).show()
////                    listener.onUpdated(uploadModel, p1)
//                }
//                builder.setNegativeButton("Detele"){
//                        dialog, which ->
////                    listener.onDeleteData(uploadModel, p1)
//                }
//
//                val dialog : AlertDialog = builder.create()
//                dialog.show()
//
//                return true
//            }
//
//        })
    }

    inner class UploadViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview){
        var ll_content : RelativeLayout
        var et_nama : TextView
        var et_description : TextView
        var image_upload : ImageView
        init {
            ll_content = itemview.findViewById(R.id.ll_content)
            et_nama = itemview.findViewById(R.id.et_nama)
            et_description = itemview.findViewById(R.id.et_description)
            image_upload = itemview.findViewById(R.id.image_upload)
        }
    }

}