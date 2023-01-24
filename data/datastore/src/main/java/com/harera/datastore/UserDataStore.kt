package com.harera.datastore

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.harera.datastore.utils.KEY
import com.harera.datastore.utils.Language
import com.harera.datastore.utils.SharedPreferences
import com.harera.datastore.utils.Value.NULL_STRING
import java.util.*

class UserDataStore(context: Context) {

    private val driverSharedPreferences =
        context.getSharedPreferences(
            SharedPreferences.USER_SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )

    @RequiresApi(Build.VERSION_CODES.GINGERBREAD)
    fun setToken(token: String) =
        driverSharedPreferences.edit().putString(KEY.TOKEN, token).apply()

    fun getToken(): String =
        driverSharedPreferences.getString(KEY.TOKEN, NULL_STRING)!!

    fun setLanguage(language: Language) {
        when (language) {
            is Language.Arabic -> {
                driverSharedPreferences.edit().putString(KEY.LANGUAGE, language.key).apply()
            }

            is Language.English -> {
                driverSharedPreferences.edit().putString(KEY.LANGUAGE, language.key).apply()
            }
        }
    }

    fun getLanguage(): String? =
        driverSharedPreferences.getString(KEY.LANGUAGE, Language.English.key)!!

    fun setUserName(name: String) {
        driverSharedPreferences.edit().putString(KEY.NAME, name).apply()
    }

    fun getUserName(): String =
        driverSharedPreferences.getString(KEY.NAME, NULL_STRING)!!


    fun setPhoneNumber(phoneNumber: String) {
        driverSharedPreferences.edit().putString(KEY.PHONE_NUMBER, phoneNumber).apply()
    }

    fun getPhoneNumber(): String =
        driverSharedPreferences.getString(KEY.PHONE_NUMBER, NULL_STRING)!!

    fun setFirstOpenState(state: Boolean) {
        driverSharedPreferences.edit().putBoolean(KEY.FIRST_OPEN, state).apply()
    }

    fun getFirstOpenState(): Boolean =
        driverSharedPreferences.getBoolean(KEY.FIRST_OPEN, true)

    fun clearData() {
        driverSharedPreferences.edit().clear().commit()
    }
}

