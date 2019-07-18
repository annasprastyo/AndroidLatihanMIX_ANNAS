package com.example.androidmix_annas.activity

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.example.androidmix_annas.R
import com.example.androidmix_annas.data.Tools
import com.example.androidmix_annas.fragments.*
import com.example.androidmix_annas.services.NotificationService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

lateinit var fAuth : FirebaseAuth
    private var toolbar: Toolbar? = null
    internal lateinit var mJobScheduler: JobScheduler

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

        toolbar = findViewById(R.id.id_toolbar) as Toolbar
        prepareActionBar(toolbar)

        id_user.setOnClickListener {
            startActivity(Intent(this, ProfilActivity::class.java))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // for system bar in lollipop
            Tools.systemBarLolipop(this)
            //Create the scheduler
            mJobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            val builder = JobInfo.Builder(1, ComponentName(packageName, NotificationService::class.java!!.getName()))
            builder.setPeriodic(900000)
            //If it needs to continue even after boot, persisted needs to be true
            //builder.setPersisted(true);
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            mJobScheduler.schedule(builder.build())
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun prepareActionBar(toolbar: Toolbar?) {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(false)
        actionBar.setHomeButtonEnabled(false)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.action_message -> {
                startActivity(Intent(this, ChatActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
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
