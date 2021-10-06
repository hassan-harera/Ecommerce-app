package com.harera.panda

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harera.panda.common.BaseActivity
import com.harera.panda.db.remote.repository.AuthManager
import com.harera.panda.db.remote.repository.UserRepository
import com.harera.panda.ui.HomeActivity
import com.harera.panda.ui.internet.NoInternetActivity
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

        mainViewModel.checkLogin()
        setupAnimation()
        waitDelay()
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
    private val authManager: AuthManager,
    private val userRepo: UserRepository
) : ViewModel() {

    val delayEnded: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoggedIn: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkLogin() {
        if (authManager.getCurrentUser() == null)
            authManager
                .loginAnonymously()
                .addOnSuccessListener {
                    isLoggedIn.value = true
                }
                .addOnFailureListener {
                    it.printStackTrace()
                }
        else
            isLoggedIn.value = true
    }

    fun startDelay() {
        viewModelScope.launch {
            delay(1500)
            delayEnded.value = true
        }
    }
}