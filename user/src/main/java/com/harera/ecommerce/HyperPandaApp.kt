package com.harera.ecommerce

import android.app.Application
import com.google.firebase.FirebaseApp
import com.harera.ecommerce.local.LocalDataSource
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class HyperPandaApp : Application() {

    @Inject
    lateinit var database: LocalDataSource

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(applicationContext)
    }
}