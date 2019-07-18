package com.example.androidmix_annas.fragments

class UploadModel {
    private var uploadBy : String? = null
    private var id : String? = null
    private var descUpload : String? = null
    private var key : String? = null
    private var imageName : String? = null
    private var fileurl : String? = null
//    private var namaLengkap : String? = null


    constructor(){}
    constructor(uploadBy : String, descUpload : String, imageName: String, fileurl: String){
        this.uploadBy = uploadBy
        this.descUpload = descUpload
        this.imageName = imageName
        this.fileurl = fileurl
    }

    fun getUploadBy() : String?{return uploadBy}
    fun getId() : String?{return id}
    fun getDescUpload() : String?{return descUpload}
    fun setId(id : String){this.id = id}
    fun setDescUpload(descUpload : String){this.descUpload = descUpload}
    fun setUploadBy(uploadBy : String){this.uploadBy = uploadBy}
    fun getKey() : String{return key!!}
    fun setKey(key : String){this.key = key}

    fun getImageName() : String?{return imageName}
    fun setImageName(imageName : String){this.imageName = imageName}

    fun getFileUrl() : String?{return fileurl}
    fun setFileUrl(fileurl : String){this.fileurl = fileurl}
}