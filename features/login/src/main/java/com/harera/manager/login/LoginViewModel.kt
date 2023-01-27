package com.harera.manager.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.harera.common.base.BaseViewModel
import com.harera.common.local.UserDataStore
import com.harera.common.utils.Validity
import com.harera.repository.abstraction.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authManager: UserRepository,
    userDataStore: UserDataStore,
) : BaseViewModel(userDataStore) {

    private var _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private var _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private var _formValidity = MutableLiveData<LoginState>()
    val formValidity: LiveData<LoginState> = _formValidity

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    private fun checkFormValidity() {
        if (_email.value.isNullOrBlank()) {
            _formValidity.value = LoginState(emailError = R.string.empty_email_error)
        } else if (!Validity.checkEmail(_email.value!!)) {
            _formValidity.value = LoginState(emailError = R.string.invalid_email_error)
        } else if (password.value.isNullOrBlank()) {
            _formValidity.value = LoginState(emailError = R.string.empty_password)
        } else if (!Validity.checkPassword(password.value!!)) {
            _formValidity.value = LoginState(passwordError = R.string.password_error)
        } else {
            _formValidity.value = LoginState(isValid = true)
        }
    }

    fun setEmail(it: String) {
        _email.value = it
        checkFormValidity()
    }

    fun setPassword(it: String) {
        _password.value = it
        checkFormValidity()
    }

    suspend fun login() {
        updateLoading(true)
        authManager
            .signInWithEmailAndPassword(email = email.value!!, password = password.value!!)
            .addOnSuccessListener {
                updateLoading(false)
                _loginSuccess.value = true
            }
            .addOnFailureListener {
                updateLoading(false)
                handleException(it)
            }
    }
}