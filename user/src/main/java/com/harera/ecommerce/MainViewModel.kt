package com.harera.ecommerce

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