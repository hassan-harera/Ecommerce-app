package com.harera.confirm_login

import android.os.Bundle
import androidx.core.view.forEach
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.harera.common.base.BaseActivity
import com.harera.common.utils.Status
import com.harera.common.navigation.utils.Arguments
import com.harera.login.R
import com.harera.login.databinding.ActivityLoginConfirmBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class ConfirmLoginActivity : BaseActivity() {

    private lateinit var waitDialog: PromptCodeDialog
    private lateinit var confirmLoginViewMode: ConfirmLoginViewModel
    private lateinit var bind: ActivityLoginConfirmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        confirmLoginViewMode = ViewModelProvider(this).get(ConfirmLoginViewModel::class.java)

        bind = ActivityLoginConfirmBinding.inflate(layoutInflater)
        setContentView(bind.root)

        observeCode()
        showWaitingDialog()
        getPhoneNumber()
        setupObservers()


        bind.gridLayout.forEach {
            it.setOnClickListener {
                when (it.id) {
                    R.id.zero -> confirmLoginViewMode.codeChanged("0")
                    R.id.one -> confirmLoginViewMode.codeChanged("1")
                    R.id.two -> confirmLoginViewMode.codeChanged("2")
                    R.id.three -> confirmLoginViewMode.codeChanged("3")
                    R.id.four -> confirmLoginViewMode.codeChanged("4")
                    R.id.five -> confirmLoginViewMode.codeChanged("5")
                    R.id.six -> confirmLoginViewMode.codeChanged("6")
                    R.id.seven -> confirmLoginViewMode.codeChanged("7")
                    R.id.eight -> confirmLoginViewMode.codeChanged("8")
                    R.id.nine -> confirmLoginViewMode.codeChanged("9")
                    R.id.remove -> confirmLoginViewMode.removeChar()
                }
            }
        }

        bind.next.setOnClickListener {
            observeOperation()
            confirmLoginViewMode.login()
        }
    }

    private fun setupObservers() {
        confirmLoginViewMode.phoneNumber.observe(this) {
            sendVerificationCode("+2$it")
        }

        confirmLoginViewMode.code.observe(this) {
            bind.code.text = it
            confirmLoginViewMode.checkCodeValidity()
        }

        confirmLoginViewMode.codeValidity.observe(this) {
            bind.next.isEnabled = it
        }
    }

    private fun getPhoneNumber() {
        intent.extras?.getString(Arguments.PHONE_NUMBER)?.let {
            confirmLoginViewMode.setPhoneNumber(it)
        }
    }

    private fun observeOperation() {
        confirmLoginViewMode.loginOperation.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    handleSuccess()
                    goToHomeActivity()
                }

                Status.ERROR -> {
                    handleFailure(it.error!!)
                }

                Status.LOADING -> {
                    handleLoading()
                }
            }
        }
    }

    private fun goToHomeActivity() {
        finish()
        overridePendingTransition(R.anim.slide_in_right, 0)
    }

    private fun showWaitingDialog() {
        waitDialog = PromptCodeDialog()
        waitDialog.show(supportFragmentManager, "null")

        GlobalScope.launch(Dispatchers.Main) {
            for (i in 30 downTo 1) {
                waitDialog.updateView(i)
                delay(1000)
            }
            waitDialog.dismiss()
        }
    }

    private fun sendVerificationCode(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(confirmLoginViewMode.createCallBack())
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun observeCode() {
        confirmLoginViewMode.verificationCode.observe(this) {
            if (it.status == Status.SUCCESS) {
                waitDialog.dismiss()
            } else if (it.status == Status.ERROR) {
                waitDialog.dismiss()
                handleFailure(it.error!!)
                showWaitingDialog()
            }
        }
    }
}