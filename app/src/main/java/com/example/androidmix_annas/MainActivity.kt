package com.example.androidmix_annas

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.androidmix_annas.fragments.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

lateinit var fAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fAuth = FirebaseAuth.getInstance()
        var menu : Menu = bottomView.menu
        selectedMenu(menu.getItem(0))
        bottomView.setOnNavigationItemSelectedListener {
                item: MenuItem ->  selectedMenu(item)

            false
        }
        val user = fAuth.currentUser!!
        if (fAuth.currentUser == null){
            et_nama_header.text =  "Guest"
        }else{
            et_nama_header.text = "${user.displayName}"
        }

        id_user.setOnClickListener {
            startActivity(Intent(this, ProfilActivity::class.java))
        }

    }

    private fun selectedMenu(item : MenuItem) {
        item.isChecked = true
        when(item.itemId) {
            R.id.item1 -> selectedFragment(Item1.getInstance())
            R.id.item2 -> selectedFragment(Item2.getInstance())
            R.id.item3 -> selectedFragment(Item3.getInstance())
            R.id.item4 -> selectedFragment(Item4.getInstance())
            R.id.item5 -> selectedFragment(Item5.getInstance())
        }
    }

    fun selectedFragment(fragment: Fragment) {
        var transaction : FragmentTransaction? =  supportFragmentManager.beginTransaction()
        transaction?.replace(R.id.rootFragment, fragment)
        transaction?.commit()
    }

    override fun onResume() {
        super.onResume()
        if (fAuth.currentUser == null) {
            finish()
        }
    }
}
