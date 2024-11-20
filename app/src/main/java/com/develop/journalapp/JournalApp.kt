package com.develop.journalapp

import android.app.Application
import android.widget.Toast
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JournalApp : Application() {

    private var fbApp : FirebaseApp? = null

    override fun onCreate() {
        super.onCreate()
        fbApp = FirebaseApp.initializeApp(this)
        if(fbApp == null){
            Toast.makeText(this, "FB_APP is Null", Toast.LENGTH_SHORT).show()
        }
    }
}