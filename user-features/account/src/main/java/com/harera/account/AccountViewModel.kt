package com.harera.account

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.harera.common.base.BaseViewModel
import com.harera.common.local.UserDataStore
import com.harera.common.utils.Validity
import com.harera.repository.abstraction.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val authManager: UserRepository,
    private val userRepository: UserRepository,
    userDataStore: UserDataStore
) : BaseViewModel(userDataStore) {

    private var _userIsAnonymous = MutableLiveData<Boolean>()
    val userIsAnonymous: LiveData<Boolean> = _userIsAnonymous

    private var _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> = _phoneNumber

    private var _numberValidity = MutableLiveData<Boolean>()
    val numberValidity: LiveData<Boolean> = _numberValidity

    private var _userImage = MutableLiveData<Bitmap>()
    val userImage: LiveData<Bitmap> = _userImage

    fun getUser() {

    }

    suspend fun checkUser() {
        _userIsAnonymous.value = authManager.getCurrentUser()!!.isAnonymous
    }

    fun setProfileImage(imageBitmap: Bitmap) {
        _userImage.value = imageBitmap
    }

    fun setPhoneNumber(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
        checkNumberValidity()
    }

    private fun checkNumberValidity() {
        _numberValidity.value = Validity.checkPhoneNumber(phoneNumber.value!!)
    }
}