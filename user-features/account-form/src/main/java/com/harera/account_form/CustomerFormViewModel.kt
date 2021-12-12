package com.harera.account_form

import android.graphics.Bitmap
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.type.LatLng
import com.harera.common.base.BaseViewModel
import com.harera.common.local.UserDataStore
import com.harera.common.utils.Response
import com.harera.common.utils.Validity
import com.harera.model.model.User
import com.harera.repository.abstraction.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class CustomerFormViewModel @Inject constructor(
    private val authManager: UserRepository,
    private val userRepository: UserRepository,
    userDataStore: UserDataStore
) : BaseViewModel(userDataStore) {

    private val _firstName: MutableLiveData<String> = MutableLiveData()
    val firstName: LiveData<String> = _firstName

    private val _codeConfirmed: MutableLiveData<Boolean> = MutableLiveData()
    val codeConfirmed: LiveData<Boolean> = _codeConfirmed

    private val _lastName: MutableLiveData<String> = MutableLiveData()
    val lastName: LiveData<String> = _lastName

    private val _phoneNumber: MutableLiveData<String> = MutableLiveData()
    val phoneNumber: LiveData<String> = _phoneNumber

    private val _image: MutableLiveData<Bitmap> = MutableLiveData()
    val image: LiveData<Bitmap> = _image

    private val _location: MutableLiveData<LatLng> = MutableLiveData()
    val location: LiveData<LatLng> = _location

    private val _imageUrl: MutableLiveData<String> = MutableLiveData()
    val imageUrl: LiveData<String> = _imageUrl

    private val _codeValidityState: MutableLiveData<Boolean> = MutableLiveData()
    val codeValidityState: LiveData<Boolean> = _codeValidityState

    private val _signupFormState = MutableLiveData<CustomerFormState>()
    val medicineFormState: LiveData<CustomerFormState> = _signupFormState

    private val _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User> = _user

    private val _medicineUpload: MutableLiveData<Any> = MutableLiveData()
    val medicineUpload: LiveData<Any> = _medicineUpload

    private val _code: MutableLiveData<String> = MutableLiveData()
    val code: LiveData<String> = _code

    fun setFirstName(name: String) {
        _firstName.value = name
        checkFormValidity()
    }

    fun setLastName(name: String) {
        _lastName.value = name
        checkFormValidity()
    }

    fun setPhoneNumber(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
        checkFormValidity()
    }

    fun setImage(imageBitmap: Bitmap) {
        _image.value = imageBitmap
        checkFormValidity()
    }

    fun setLocation(location: LatLng) {
        _location.value = location
        checkFormValidity()
    }

    private fun checkFormValidity() {
        if (_firstName.value.isNullOrBlank()) {
            _signupFormState.value = CustomerFormState(firstNameError = R.string.empty_name_error)
        } else if (_lastName.value.isNullOrBlank()) {
            _signupFormState.value = CustomerFormState(lastNameError = R.string.empty_name_error)
        } else if (phoneNumber.value.isNullOrBlank()) {
            _signupFormState.value =
                CustomerFormState(phoneNumberError = R.string.empty_phone_error)
        } else if (_location.value == null) {
            _signupFormState.value = CustomerFormState(addressError = R.string.empty_location_error)
        } else {
            _signupFormState.value = CustomerFormState(isValid = true)
        }
    }

    suspend fun addUser(url: String) {
        _medicineUpload.postValue(Response.loading(null))
        userRepository
            .addUser(
                user.value!!.apply { imageUrl = url }
            )
            .onSuccess {
                _medicineUpload.value = Response.success(null)
            }
            .onFailure {
                _medicineUpload.postValue(Response.error(it, null))
            }
    }

    suspend fun uploadUserImage(user: User) {
        updateLoading(true)
        userRepository
            .uploadUserImage(
                image.value!!,
                user.uid
            )
            .onSuccess {
                _imageUrl.postValue(it)
            }
            .onFailure {
                handleException(it)
            }
    }

    fun encapsulateUserForm() {
        updateLoading(true)

        _user.value = User(
            phoneNumber = phoneNumber.value!!,
            imageUrl = imageUrl.value,
            firstName = firstName.value!!,
            lastName = lastName.value!!,
            long = location.value!!.longitude,
            lat = location.value!!.latitude,
        )
    }

    suspend fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
    ) = authManager.signInWithCredential(credential)

    suspend fun removeUser(userID: String) {
        userRepository.removeUser(userID)
    }

    fun createCredential(code: String, verificationId: String) =
        PhoneAuthProvider.getCredential(verificationId, code)

    fun sendVerificationCode(
        phoneNumber: String,
        activity: FragmentActivity,
    ) {
        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(createCallBack)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun setCode(code: String) {
        _code.value = code
        checkCodeValidity()
    }

    private fun checkCodeValidity() {
        _codeValidityState.value = Validity.checkCodeValidity(code.value!!)
    }

    fun setCodeConfirmed(state: Boolean) {
        _codeConfirmed.value = state
    }

    private val createCallBack =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            }

            override fun onVerificationFailed(e: FirebaseException) {
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken,
            ) {
//                handleSuccess()
//                verficationCode = verificationId
//                oldUid = authManager.getCurrentUser()!!.uid
//                phoneUser.name = firstName + lastName
//                phoneUser.phoneNumber = phoneNumberWithCode
//                promptCode()
            }
        }

}