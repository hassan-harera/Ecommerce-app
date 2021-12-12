package com.harera.common.base

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.harera.common.R
import com.harera.common.ui.LoadingDialog

open class BaseViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    val loadingDialog : LoadingDialog by lazy {
        LoadingDialog(
            binding.root.context
        )
    }

    fun handleFailure(e: Exception) {
        dismissLoading()
        showErrorToast()
        printException(e)
    }

    fun handleLoading() {
        showLoading()
    }

    fun handleSuccess() {
        dismissLoading()
    }

    private fun showErrorToast() {
        Toast.makeText(
            binding.root.context,
            binding.root.context.resources.getText(R.string.error_toast),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun printException(e: Exception) {
        e.printStackTrace()
    }

    private fun showLoading() {
        loadingDialog.show()
    }

    private fun dismissLoading() {
        loadingDialog.dismiss()
    }
}