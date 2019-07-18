package com.example.androidmix_annas.services

import android.util.Log.d
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService

class FirebaseInstncIDServices : FirebaseMessagingService(){
    private val FRIENDLY_ENGAGE_TOPIC = "friendly_engage"

    override fun onNewToken(s: String?) {
        super.onNewToken(s)
        FirebaseMessaging.getInstance().subscribeToTopic(FRIENDLY_ENGAGE_TOPIC)
        d("NEW_TOKEN", s)
    }

}