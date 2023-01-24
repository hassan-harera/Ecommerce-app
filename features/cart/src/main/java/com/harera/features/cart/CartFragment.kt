package com.harera.features.cart

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.harera.cart_item.CartAdapter
import com.harera.common.base.BaseFragment
import com.harera.common.utils.navigation.Arguments
import com.harera.common.utils.navigation.Destinations
import com.harera.common.utils.navigation.NavigationUtils
import com.harera.features.cart.databinding.FragmentCartBinding
import com.harera.model.model.CartItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : BaseFragment() {
    private val TAG = "CartFragment"
    private val cartViewModel: CartViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter
    private lateinit var bind: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        bind = FragmentCartBinding.inflate(layoutInflater, container, false)
        setupCartAdapter()
        setupListeners()

        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartViewModel.cartList.observe(viewLifecycleOwner) {
            updateCartList(it)
        }

        cartViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }

        cartViewModel.exception.observe(viewLifecycleOwner) {
            handleError(it)
        }

        connectionLiveData.observe(viewLifecycleOwner) {
            cartViewModel.updateConnectivity(it)
        }

        lifecycleScope.launch(Dispatchers.IO) {
            cartViewModel.getCartItems()
        }
    }

    private fun updateCartList(cartList: List<CartItem>) {
        Log.d(TAG, "out")
        if (cartList.isNotEmpty()) {
            Log.d(TAG, "is Not Empty")
            bind.emptyList.root.visibility = View.INVISIBLE
            bind.carts.visibility = View.VISIBLE
            bind.checkout.visibility = View.VISIBLE
            cartAdapter.setCartList(cartList)
        } else {
            Log.d(TAG, "is Empty")
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
            onRemoveItemClicked = { cartItemId ->
                lifecycleScope.launch(Dispatchers.IO) {
                    cartViewModel.removeCartItem(cartItemId)
                }
            },
            onItemClicked = { productId ->
                viewProduct(productId)
            },
            onMoveToFavouriteClicked = { cartItemId ->
                lifecycleScope.launch(Dispatchers.IO) {
                    cartViewModel.moveToFavourite(cartItemId)
                }
            },
            onQuantityMinusClicked = { cartItemId ->
                lifecycleScope.launchWhenCreated {
                    cartViewModel.minusQuantity(cartItemId)
                }
            },
            onQuantityPlusClicked = { cartItemId ->
                lifecycleScope.launchWhenCreated {
                    cartViewModel.plusQuantity(cartItemId)
                }
            }
        )

        bind.carts.setHasFixedSize(true)
        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        bind.carts.addItemDecoration(itemDecorator)
        bind.carts.adapter = cartAdapter
    }
}