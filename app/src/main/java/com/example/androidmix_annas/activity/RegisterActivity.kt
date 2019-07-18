package com.example.androidmix_annas.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.androidmix_annas.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.layout_register.*

class RegisterActivity : AppCompatActivity() {

    lateinit var fAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_register)

        fAuth = FirebaseAuth.getInstance()

        btn_register.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if (email.isNotEmpty() || password.isNotEmpty() ||
                !email.equals("") || password.equals("")){
                fAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener{
                        if (it.isSuccessful){
                            Toast.makeText(this, "REGISTER BERHASIL",
                                Toast.LENGTH_SHORT).show()
                            onBackPressed()
                        }else{
                            Toast.makeText(this, "REGISTER GAGAL",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            }else{
                Toast.makeText(this, "email atau password tak boleh kosong",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}