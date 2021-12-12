package com.harera.firebase.impl

import android.graphics.Bitmap
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.harera.common.utils.BitmapUtils
import com.harera.common.utils.BitmapUtils.Companion.convertImageBitmapToByteArray
import com.harera.model.model.User
import com.harera.firebase.abstraction.DBConstants
import com.harera.firebase.abstraction.UserService
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class FirebaseUserService @Inject constructor(
    private val fStore: FirebaseFirestore,
    private val fStorage: FirebaseStorage,
) : UserService {

    override suspend fun addUser(user: User): Boolean =
        fStore.collection("Users")
            .document(user.uid)
            .set(user)
            .let {
                Tasks.await(it)
                it.isSuccessful
            }

    override suspend fun removeUser(userId: String): Boolean =
        fStore.collection("Users")
            .document(userId)
            .delete()
            .let {
                Tasks.await(it)
                it.isSuccessful
            }

    override suspend fun getUser(uid: String): User =
        fStore.collection("Users")
            .document(uid)
            .get()
            .let {
                Tasks.await(it).toObject(User::class.java)!!
            }

    override suspend fun uploadUserImage(imageBitmap: Bitmap, uid: String): String {
        return fStorage
            .reference
            .child(DBConstants.USERS)
            .child(uid)
            .putBytes(convertImageBitmapToByteArray(imageBitmap))
            .let {
                Tasks.await(it).storage.downloadUrl.let { Tasks.await(it).toString() }
            }
    }
}
