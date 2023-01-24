package com.harera.datastore.di

import android.app.Application
import android.content.Context
import com.harera.datastore.UserDataStore
import org.koin.dsl.module


val dataStoreModule = module {

    single<Context> {
        (get() as Application).applicationContext
    }

    single {
        UserDataStore(get())
    }
}