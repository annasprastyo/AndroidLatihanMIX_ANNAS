package com.example.androidmix_annas

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.layout_profil.*

class ProfilActivity : AppCompatActivity() {

    lateinit var fAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_profil)

        fAuth = FirebaseAuth.getInstance()

        btn_logout.setOnClickListener {
            fAuth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        btn_back.setOnClickListener {
            onBackPressed()
        }
    }
}