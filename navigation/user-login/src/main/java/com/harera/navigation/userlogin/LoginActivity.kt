package com.harera.navigation.userlogin

import android.os.Bundle
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.harera.common.base.BaseActivity
import com.harera.common.navigation.utils.Arguments
import com.harera.common.navigation.utils.Destination
import com.harera.common.navigation.utils.Domain
import com.harera.common.navigation.utils.NavigationUtils
import com.harera.navigation.userlogin.databinding.ActivityLoginBinding


class LoginActivity : BaseActivity() {

    private val TAG = "HomeActivity"
    private lateinit var binding: ActivityLoginBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = Navigation.findNavController(this, R.id.navView)

        getExtras()
    }

    private fun getExtras() {
        intent?.extras?.getString(Arguments.DESTINATION)?.let {
            navController.navigate(
                NavigationUtils.getUriNavigation(
                    Domain.ENGLIZYA_PAY,
                    it,
                    Destination.HOME
                ).toUri()
            )
        }
    }
}