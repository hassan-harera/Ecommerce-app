package com.harera.manager.dashboard

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.harera.common.base.BaseFragment
import com.harera.common.navigation.utils.Arguments
import com.harera.common.navigation.utils.Destinations
import com.harera.common.navigation.utils.NavigationUtils
import com.harera.manager.dashboard.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardFragment : BaseFragment() {

    private lateinit var bind: FragmentDashboardBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentDashboardBinding.inflate(inflater)
        navController = findNavController()
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        bind.addCategory.setOnClickListener {
            navController.navigate(
                Uri.parse(
                    NavigationUtils.getUriNavigation(
                        Arguments.HYPER_PANDA_MANAGER_DOMAIN,
                        Destinations.ADD_CATEGORY,
                        null
                    )
                )
            )
        }

        bind.showProducts.setOnClickListener {
            navController.navigate(
                Uri.parse(
                    NavigationUtils.getUriNavigation(
                        Arguments.HYPER_PANDA_MANAGER_DOMAIN,
                        Destinations.CATEGORIES,
                        null
                    )
                )
            )
        }

        bind.addProduct.setOnClickListener {

        }

        bind.addDeliveryMan.setOnClickListener {

        }

        bind.addOffer.setOnClickListener {

        }

        bind.addOfferCollection.setOnClickListener {

        }

        bind.orders.setOnClickListener {

        }
    }
}