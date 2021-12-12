package com.harera.firebase.abstraction

import android.graphics.Bitmap
import com.google.firebase.storage.UploadTask
import com.harera.model.model.User

interface UserService {

    suspend fun addUser(user: User): Boolean
    suspend fun removeUser(userId: String): Boolean
    suspend fun getUser(uid: String): User
    suspend fun uploadUserImage(imageBitmap: Bitmap, uid: String): String
}