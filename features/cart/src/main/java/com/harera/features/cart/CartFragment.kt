package com.harera.features.cart

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.harera.common.base.BaseFragment
import com.harera.common.utils.navigation.Arguments
import com.harera.common.utils.navigation.Destinations
import com.harera.common.utils.navigation.NavigationUtils
import com.harera.features.cart.databinding.FragmentCartBinding
import com.harera.repository.domain.CartItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment() {

    private val cartViewModel: CartViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter
    private lateinit var bind: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        bind = FragmentCartBinding.inflate(layoutInflater, container, false)
        setupCartAdapter()
        setupListeners()

        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartViewModel.getCartItems()

        cartViewModel.cartList.observe(viewLifecycleOwner) {
            updateCartList(it.map { it.value })
        }

        cartViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }

        cartViewModel.exception.observe(viewLifecycleOwner) {
            handleError(it)
        }

        cartViewModel.cartList.observe(viewLifecycleOwner) {
            updateCartList(it.map { it.value })
        }
    }

    private fun updateCartList(cartList: List<CartItem>) {
        if(cartList.isNotEmpty()) {
            bind.emptyList.root.visibility = View.INVISIBLE
            bind.carts.visibility = View.VISIBLE
            bind.checkout.visibility = View.VISIBLE
            cartAdapter.setCartList(cartList)
        } else {
            bind.emptyList.root.visibility = View.VISIBLE
            bind.carts.visibility = View.INVISIBLE
            bind.checkout.visibility = View.INVISIBLE
        }
    }

    private fun setupListeners() {
        bind.emptyList.cartShopping.setOnClickListener {
            goShop()
        }
    }

    private fun goShop() {
        findNavController().navigate(
            Uri.parse(
                NavigationUtils.getUriNavigation(
                    Arguments.HYPER_PANDA_DOMAIN,
                    Destinations.SHOP,
                    null
                )
            )
        )
    }

    private fun viewProduct(productId: String) {
        findNavController().navigate(
            Uri.parse(
                NavigationUtils.getUriNavigation(
                    Arguments.HYPER_PANDA_DOMAIN,
                    Destinations.PRODUCT,
                    productId
                )
            )
        )
    }

    private fun setupCartAdapter() {
        cartAdapter = CartAdapter(
            emptyList(),
            onRemoveItemClicked = {
                cartViewModel.removeItem(it)
            },
            onItemClicked = { productId ->
                viewProduct(productId)
            },
            onMoveToFavouriteClicked = {
                cartViewModel.moveToFavourite(it)
            },
            onQuantityMinusClicked = {
                cartViewModel.minusQuantity(it)
            },
            onQuantityPlusClicked = {
                cartViewModel.plusQuantity(it)
            }
        )
        bind.carts.setHasFixedSize(true)
        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        bind.carts.addItemDecoration(itemDecorator)
        bind.carts.adapter = cartAdapter
    }
}