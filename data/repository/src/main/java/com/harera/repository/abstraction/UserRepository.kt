package com.harera.repository.abstraction

import android.graphics.Bitmap
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.harera.model.model.User

interface UserRepository {

    suspend fun addUser(user: User): Result<Boolean>
    suspend fun removeUser(uid: String): Result<Boolean>
    suspend fun getUser(uid: String): Result<User>
    suspend fun uploadUserImage(imageBitmap: Bitmap, uid: String): Result<String>

    suspend fun signIn()
    suspend fun loginAnonymously(): Result<Boolean>
    suspend fun signOut()
    suspend fun signInWithCredential(credential: PhoneAuthCredential): Task<AuthResult>
    suspend fun getCurrentUser(): FirebaseUser?
    suspend fun sendVerificationCode(
        phoneNumber: String,
        callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks,
    ): Result<Unit>

    suspend fun updatePassword(password: String): Result<Boolean>
    suspend fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult>
    suspend fun createCredential(verificationId: String, code: String): PhoneAuthCredential
    suspend fun login(credential: AuthCredential): Task<AuthResult>
}