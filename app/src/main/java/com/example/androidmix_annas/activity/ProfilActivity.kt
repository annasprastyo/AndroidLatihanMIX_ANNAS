package com.example.androidmix_annas.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.example.androidmix_annas.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.layout_profil.*

class ProfilActivity : AppCompatActivity() {

    lateinit var fAuth : FirebaseAuth
    private var actionBar: ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_profil)

        fAuth = FirebaseAuth.getInstance()
        val user = fAuth.currentUser!!
        et_nama_profil.text = user!!.displayName
        et_kontak.text = user!!.email

        btn_logout.setOnClickListener {
            fAuth.signOut()
            val logoutIntent = Intent(this, LoginActivity::class.java)
            logoutIntent.putExtra("mode", "logout")
            startActivity(logoutIntent)
            finish()
        }

        initToolbar()
    }

    fun initToolbar() {
        val toolbar = findViewById(R.id.id_toolbar1) as Toolbar
        setSupportActionBar(toolbar)
        actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setHomeButtonEnabled(true)
        actionBar!!.setTitle(" ")
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home ->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}