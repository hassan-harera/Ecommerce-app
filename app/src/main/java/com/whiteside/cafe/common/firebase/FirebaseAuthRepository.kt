package com.whiteside.cafe.common.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.whiteside.cafe.common.repository.AuthManager
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor() : AuthManager {
    val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun signIn() {
        TODO("Not yet implemented")
    }

    override fun signInAnonymously(): Task<AuthResult> = auth.signInAnonymously()

    override fun signInPhoneNumber() {
        TODO("Not yet implemented")
    }

    override fun signOut() = auth.signOut()
}