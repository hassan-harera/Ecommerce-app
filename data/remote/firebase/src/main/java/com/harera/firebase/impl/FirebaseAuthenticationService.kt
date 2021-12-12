package com.harera.firebase.impl

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.*
import com.harera.firebase.abstraction.AuthenticationService
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FirebaseAuthenticationService @Inject constructor(
    private val auth: FirebaseAuth
) : AuthenticationService {

    override fun signIn() {}

    override fun loginAnonymously(): Boolean =
        auth.signInAnonymously().let {
            Tasks.await(it)
            it.isSuccessful
        }

    override fun signInByPhoneNumber() {}

    override fun signOut() = auth.signOut()

    override fun signInWithCredential(credential: PhoneAuthCredential) =
        auth.signInWithCredential(credential)

    override fun getCurrentUser(): FirebaseUser? = auth.currentUser

    override fun sendVerificationCode(
        phoneNumber: String,
        callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setCallbacks(callback)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun updatePassword(password: String): Boolean =
        auth.currentUser!!
            .updatePassword(password)
            .let {
                Tasks.await(it)
                it.isSuccessful
            }

    override fun signInWithEmailAndPassword(email: String, password : String): Task<AuthResult> =
        auth.signInWithEmailAndPassword(email, password)

    override fun createCredential(verificationId : String, code: String) =
        PhoneAuthProvider.getCredential(verificationId, code)

    override fun login(credential: AuthCredential) =
        auth.signInWithCredential(credential)
}