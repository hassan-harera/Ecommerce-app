package com.harera.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harera.common.local.UserDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(
    private val userDataStore: UserDataStore,
) : ViewModel() {

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _exception: MutableLiveData<Exception?> = MutableLiveData()
    val exception: LiveData<Exception?> = _exception

    private val _connectivity: MutableLiveData<Boolean> = MutableLiveData(false)
    val connectivity: LiveData<Boolean> = _connectivity

    val uid: String = userDataStore.getUid()!!

    val token: String? = userDataStore.getToken()

    fun handleException(exception: Exception?) {
        exception?.printStackTrace()
    }

    fun updateLoading(state: Boolean) {
        _loading.postValue(state)
    }

    fun handleException(exception: Throwable?) {
        exception?.printStackTrace()
    }

    fun updateConnectivity(connectivity: Boolean) {
        _connectivity.postValue(connectivity)
    }
}