package com.harera.common.base

import android.content.Intent
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.harera.common.R
import com.harera.common.ui.LoadingDialog

open class BaseActivity : AppCompatActivity() {
    private val loadingDialog: LoadingDialog by lazy {
        LoadingDialog(
            this
        )
    }

    fun handleLoading() {
        showLoading()
    }

    fun handleSuccess() {
        dismissLoading()
    }

    private fun showErrorToast() {
        Toast.makeText(this, resources.getText(R.string.error_toast), Toast.LENGTH_SHORT).show()
    }

    fun hideSoftKeyboard() {
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    fun handleFailure(exception: Exception?) {
        exception?.printStackTrace()
        Toast.makeText(this, resources.getText(R.string.error_toast), Toast.LENGTH_SHORT).show()
    }

    private fun printException(e: Exception?) {
        e?.printStackTrace()
    }

    private fun showLoading() {
        loadingDialog.show()
    }

    private fun dismissLoading() {
        loadingDialog.dismiss()
    }

    fun <T : AppCompatActivity> gotToActivity(activityClass: Class<T>) {
        startActivity(Intent(this, activityClass))
            .also { finish() }
    }
}
