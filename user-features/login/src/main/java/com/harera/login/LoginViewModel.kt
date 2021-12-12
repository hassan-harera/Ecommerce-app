package com.harera.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.harera.common.base.BaseViewModel
import com.harera.common.local.UserDataStore
import com.harera.common.utils.Validity
import com.harera.repository.abstraction.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authManager: UserRepository,
    userDataStore: UserDataStore,
) : BaseViewModel(userDataStore) {
    private var _phoneNumber = MutableLiveData<String>("")
    val phoneNumber: LiveData<String> = _phoneNumber

    private var _phoneNumberValidity = MutableLiveData<Boolean>(false)
    val phoneNumberValidity: LiveData<Boolean> = _phoneNumberValidity

    private var _policyAccepted = MutableLiveData<Boolean>(false)
    val policyAccepted: LiveData<Boolean> = _policyAccepted

    fun changePhoneNumber(ch: String) {
        phoneNumber.value?.let {
            if (it.length < 11) {
                _phoneNumber.value = it.plus(ch)
            }
        }
    }

    fun checkPhoneNumberValidity() {
        _phoneNumberValidity.value = Validity.checkPhoneNumber(_phoneNumber.value!!)
    }

    fun acceptPolicy() {
        _policyAccepted.value = !_policyAccepted.value!!
    }

    fun removeChar() {
        if (_phoneNumber.value!!.isNotEmpty()) {
            _phoneNumber.value = _phoneNumber.value!!.dropLast(1)
        }
    }

    fun isLoginAnonymously(): Boolean =
        runBlocking {
            authManager.getCurrentUser()!!.isAnonymous
        }

    fun setPhoneNumber(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
        checkPhoneNumberValidity()
    }
}