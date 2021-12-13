package com.harera.ecommerce

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.harera.common.base.BaseActivity
import com.harera.common.base.BaseViewModel
import com.harera.common.internet.NoInternetActivity
import com.harera.common.local.UserDataStore
import com.harera.repository.abstraction.UserRepository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupAnimation()
        waitDelay()

        lifecycleScope.launch(Dispatchers.IO) {
            mainViewModel.checkLogin()
        }
    }

    private fun waitDelay() {
        mainViewModel.startDelay()

        mainViewModel.delayEnded.observe(this) { delayFinished ->
            if (delayFinished) {
                mainViewModel.isLoggedIn.observe(this) { isLoggedIn ->
                    if (isLoggedIn) {
                        finishActivity()
                    }
                }
            }
        }
    }

    private fun finishActivity() {
        startActivity(
            Intent(
                this@MainActivity,
                HomeActivity::class.java
            )
        ).also {
            finish()
        }
    }

    private fun setupAnimation() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.waiting_page_transition)
        val appName = findViewById<TextView?>(R.id.app_name)
        val apple = findViewById<ImageView?>(R.id.apple)
        apple.startAnimation(animation)
        appName.startAnimation(animation)
    }

    private fun showNoInternet() {
        val intent = Intent(this@MainActivity, NoInternetActivity::class.java)
        startActivity(intent)
        finish()
    }
}


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