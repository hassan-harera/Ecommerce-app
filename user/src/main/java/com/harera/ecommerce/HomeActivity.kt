package com.harera.ecommerce

import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.harera.common.base.BaseActivity
import com.harera.ecommerce.databinding.ActivityHomeBinding
import com.harera.ecommerce.databinding.NavigationContentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    private lateinit var navController: NavController
    private lateinit var navContent: NavigationContentBinding
    private lateinit var bind: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(bind.root)
        navContent = bind.content

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bind.navView.setupWithNavController(navController = navController)
        NavigationUI.setupWithNavController(navContent.bottomNav, navController)

        setUpListeners()
    }

    private fun setUpListeners() {
        bind.navView.bringToFront()
        bind.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_wishlist -> {
                    navController.navigate(R.id.navigation_wishlist)
                }

                R.id.navigation_cart -> {
                    navController.navigate(R.id.navigation_cart)
                }

                R.id.navigation_search -> {
                    navController.navigate(R.id.navigation_search)
                }

                R.id.navigation_profile -> {
                    login()
                }
            }
            return@setNavigationItemSelectedListener true
        }

        navContent.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.navigation_search -> {
                    navController.navigate(R.id.navigation_search)
                }

                R.id.navigation_menu -> {
                    bind.drawerLayout.openDrawer(GravityCompat.END)
                }
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun login() {

    }

    override fun onBackPressed() {
        navController.popBackStack()
    }
}