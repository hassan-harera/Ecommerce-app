package com.harera.firebase.abstraction

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*

interface AuthenticationService {

    fun signIn()
    fun loginAnonymously(): Boolean
    fun signInByPhoneNumber()
    fun signOut()
    fun signInWithCredential(credential: PhoneAuthCredential): Task<AuthResult>
    fun getCurrentUser(): FirebaseUser?
    fun sendVerificationCode(
        phoneNumber: String,
        callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    )
    fun updatePassword(password: String): Boolean
    fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult>
    fun createCredential(verificationId: String, code: String): PhoneAuthCredential
    fun login(credential: AuthCredential): Task<AuthResult>
}