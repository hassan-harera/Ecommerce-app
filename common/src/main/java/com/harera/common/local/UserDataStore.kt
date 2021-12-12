package com.harera.common.local

import android.content.Context
import android.content.SharedPreferences
import com.harera.common.local.LocalStoreConstants.NULL
import com.harera.common.local.LocalStoreConstants.TOKEN
import com.harera.common.local.LocalStoreConstants.UID
import com.harera.common.local.LocalStoreConstants.USER

object LocalStoreConstants {
    const val USER = "user"
    const val UID = "uid"
    const val TOKEN = "token"
    const val NULL = "null"
}

class UserDataStore(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences(USER, Context.MODE_PRIVATE)

    fun getUid() : String? = sharedPreferences.getString(UID, NULL)
    fun getToken() : String? = sharedPreferences.getString(TOKEN, NULL)
}