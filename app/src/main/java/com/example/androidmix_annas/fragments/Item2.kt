package com.example.androidmix_annas.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.androidmix_annas.PrefsHelper
import com.example.androidmix_annas.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.layout_item2.*
import java.io.IOException
import java.util.*

class Item2 : Fragment() {
    lateinit var fAuth : FirebaseAuth
    lateinit var dbRef : DatabaseReference
    lateinit var helperPref : PrefsHelper
    var filepath : String = ""

    val REQUEST_IMAGE = 10002
    val PERMISSION_REQUEST_CODE = 10003
    lateinit var filePathImage : Uri
    var value = 0.0

    lateinit var fstorage : FirebaseStorage
    lateinit var fstorageRef : StorageReference
    companion object {
        fun getInstance() : Item2 =
            Item2()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.layout_item2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        helperPref = PrefsHelper(activity!!)
        fstorage = FirebaseStorage.getInstance()
        fstorageRef = fstorage.reference

        image_placeholder.setOnClickListener {
            when{
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) ->{
                    if (ContextCompat.checkSelfPermission(
                            activity!!,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(arrayOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE),
                            PERMISSION_REQUEST_CODE)
                    }else{
                        imageChooser()
                    }
                }
                else -> {
                    imageChooser()
                }
            }
        }

        fAuth = FirebaseAuth.getInstance()

        btn_upload.setOnClickListener {
            val desc = et_description.text.toString()
            val nameX = UUID.randomUUID().toString()

            if (desc.isNotEmpty()){
                uploadDatas(desc, nameX)
            }else{
                Toast.makeText(activity!!, "input tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun imageChooser(){
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(Intent.createChooser(intent, "select image"), REQUEST_IMAGE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_REQUEST_CODE ->{
                if (grantResults.isEmpty() || grantResults[0]
                    == PackageManager.PERMISSION_DENIED){
                    Toast.makeText(activity!!, "izin ditolak!!", Toast.LENGTH_SHORT).show()
                }else{
                    imageChooser()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK){
            return
        }
        when(requestCode){
            REQUEST_IMAGE ->{
                filePathImage = data?.data!!
                try {
                    val bitmap : Bitmap = MediaStore.Images.Media.getBitmap(
                        activity!!.contentResolver, filePathImage)
                    Glide.with(this)
                        .load(bitmap)
                        .override(250, 250)
                        .centerCrop()
                        .into(image_placeholder)
                }catch (x : IOException){
                    x.printStackTrace()
                }
            }
        }
    }

    fun GetFileExtension(uri : Uri) : String?{
        val contentResolverz = activity!!.contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()

        return mimeTypeMap.getExtensionFromMimeType(contentResolverz.getType(uri))
    }

    fun uploadDatas(desc: String, nameX : String){
//        val nameX = UUID.randomUUID().toString()
        val uid = helperPref.getUI()
        val ref : StorageReference = fstorageRef
            .child("images/$uid/${nameX}.${GetFileExtension(filePathImage)}")
        ref.putFile(filePathImage)
            .addOnSuccessListener {

                ref.downloadUrl.addOnSuccessListener {
                    filepath = it.toString();
                    simpanToFireBase(desc, nameX, filepath)
//                    Toast.makeText(activity!!, it.toString(), Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(view!!.context, "berhasil upload",
                    Toast.LENGTH_SHORT).show()
                progressDownload.visibility = View.GONE
            }
            .addOnFailureListener {
                Log.e("TAGERROR", it.message)
            }
            .addOnProgressListener {
                    taskSnapshot ->
                value = (100.0*taskSnapshot.bytesTransferred/taskSnapshot.totalByteCount)
                progressDownload.visibility = View.VISIBLE
            }
            .addOnCompleteListener {

            }
    }

    fun simpanToFireBase(desc: String, namex: String, fileurl: String){
        val uidUser = helperPref.getUI()
        val counterID = helperPref.getCounterId()
        val user = fAuth.currentUser!!
        dbRef = FirebaseDatabase.getInstance().getReference("dataUploads/$counterID")
        dbRef.child("id").setValue(uidUser)
        dbRef.child("uploadBy").setValue(user!!.displayName)
        dbRef.child("descUpload").setValue(desc)
        dbRef.child("imageName").setValue(namex)
        dbRef.child("fileurl").setValue(fileurl)

        Toast.makeText(view!!.context, "Data berhasil ditambah",
            Toast.LENGTH_SHORT).show()

        helperPref.saveCounterId(counterID+1)
    }

}