package com.example.androidmix_annas.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.example.androidmix_annas.R
import com.google.firebase.auth.FirebaseAuth

/**
 * A sample splash screen created by devdeeds.com
 * by Jayakrishnan P.M
 */
class SplashActivity : AppCompatActivity() {
    private var mDelayHandler: Handler? = null
    private lateinit var fAuth : FirebaseAuth
    private val SPLASH_DELAY: Long = 3000 //3 seconds

    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //Initialize the Handler
        fAuth = FirebaseAuth.getInstance()
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)

    }

    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        if (fAuth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}