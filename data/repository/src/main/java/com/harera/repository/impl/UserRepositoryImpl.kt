package com.harera.repository.impl

import android.graphics.Bitmap
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.harera.firebase.abstraction.AuthenticationService
import com.harera.firebase.abstraction.UserService
import com.harera.model.model.User
import com.harera.repository.abstraction.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val userService: UserService,
) : UserRepository {

    override suspend fun signIn() {}

    override suspend fun loginAnonymously(): Result<Boolean> = kotlin.runCatching {
        authenticationService.loginAnonymously()
    }

    override suspend fun signOut() {
        authenticationService.signOut()
    }

    override suspend fun signInWithCredential(credential: PhoneAuthCredential) =
        authenticationService.signInWithCredential(credential)

    override suspend fun getCurrentUser(): FirebaseUser? {
        return authenticationService.getCurrentUser()
    }

    override suspend fun sendVerificationCode(
        phoneNumber: String,
        callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks,
    ): Result<Unit> = kotlin.runCatching {
        authenticationService.sendVerificationCode(phoneNumber, callback)
    }

    override suspend fun updatePassword(password: String): Result<Boolean> = kotlin.runCatching {
        authenticationService.updatePassword(password)
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String,
    ): Task<AuthResult> =
        authenticationService.signInWithEmailAndPassword(email, password)

    override suspend fun createCredential(verificationId: String, code: String) =
        PhoneAuthProvider.getCredential(verificationId, code)

    override suspend fun login(credential: AuthCredential) =
        authenticationService.login(credential)

    override suspend fun addUser(user: User): Result<Boolean> = kotlin.runCatching {
        userService.addUser(user)
    }

    override suspend fun removeUser(uid: String): Result<Boolean> = kotlin.runCatching {
        userService.removeUser(uid)
    }

    override suspend fun getUser(uid: String): Result<User> = kotlin.runCatching {
        userService.getUser(uid)
    }

    override suspend fun uploadUserImage(imageBitmap: Bitmap, uid: String): Result<String> =
        kotlin.runCatching {
            userService.uploadUserImage(imageBitmap, uid)
        }
}