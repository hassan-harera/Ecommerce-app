package com.harera.ecommerce

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.harera.common.base.BaseViewModel
import com.harera.common.local.UserDataStore
import com.harera.repository.abstraction.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepo: UserRepository,
    userDataStore: UserDataStore,
) : BaseViewModel(userDataStore) {

    val delayEnded: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoggedIn: MutableLiveData<Boolean> = MutableLiveData(false)

    suspend fun checkLogin() {
        if (userRepo.getCurrentUser() == null)
            userRepo
                .loginAnonymously()
                .onSuccess {
                    isLoggedIn.postValue(!it)
                }
                .onFailure {
                    handleException(it)
                }
        else
            isLoggedIn.postValue(true)
    }

    fun startDelay() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1500)
            delayEnded.postValue(true)
        }
    }
}