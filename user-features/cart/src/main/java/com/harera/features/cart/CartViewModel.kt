package com.harera.features.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.harera.common.base.BaseViewModel
import com.harera.common.local.UserDataStore
import com.harera.model.model.CartItem
import com.harera.model.model.WishItem
import com.harera.repository.abstraction.CartRepository
import com.harera.repository.abstraction.ProductRepository
import com.harera.repository.abstraction.UserRepository
import com.harera.repository.abstraction.WishListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.joda.time.DateTime
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val wishListRepository: WishListRepository,
    private val authManager: UserRepository,
    private val productRepository: ProductRepository,
    userDataStore: UserDataStore,
) : BaseViewModel(userDataStore) {

    private val _cartList = MutableLiveData<List<CartItem>>()
    val cartList: LiveData<List<CartItem>> = _cartList

    suspend fun removeCartItem(cartItemId: String) {
        updateLoading(true)

        cartRepository
            .removeCartItem(uid)
            .onSuccess {
                updateLoading(false)
                _cartList.apply {
                    postValue(value?.dropWhile { it.cartItemId == cartItemId })
                }
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    suspend fun getCartItems() {
        updateLoading(true)

        cartRepository
            .getUserCartItems(authManager.getCurrentUser()!!.uid)
            .onSuccess {
                updateLoading(false)
                _cartList.postValue(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    suspend fun updateQuantity(cartItemId: String, quantity: Int) {
        updateLoading(true)

        val item = cartList.value?.first { it.cartItemId == cartItemId }!!

        cartRepository
            .updateQuantity(cartItemId, quantity)
            .onSuccess {
                updateLoading(false)
                _cartList.apply {
                    item.quantity = quantity
                    postValue(value)
                }
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    private fun updateCartList(list: List<CartItem>) {
        _cartList.postValue(list)
    }

    private suspend fun addItemToFavourite(cartItemId: String) {
        updateLoading(true)

        val product = cartList.value!!.first { cartItemId == it.cartItemId }

        wishListRepository
            .addWishListItem(
                WishItem(
                    uid = uid,
                    productPrice = product.productPrice,
                    productTitle = product.productTitle,
                    productImageUrl = product.productImageUrl,
                    productId = product.productId,
                    time = DateTime.now().toString()
                )
            ).onSuccess { isSuccessful ->
                updateLoading(false)
                if (isSuccessful) {
                    removeCartItem(cartItemId)
                }
            }.onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    suspend fun moveToFavourite(cartItemId: String) {
        addItemToFavourite(cartItemId)
    }

    suspend fun plusQuantity(cartItemId: String) {
        val newQuantity = cartList.value!!.first { it.cartItemId == cartItemId }.quantity + 1

        cartRepository
            .updateQuantity(cartItemId, newQuantity)
            .onSuccess {
                updateCartItemQuantity(cartItemId, newQuantity)
            }
            .onFailure {
                handleException(it)
            }
    }

    private fun updateCartItemQuantity(cartItemId: String, newQuantity: Int) {
        cartList.value!!.first { it.cartItemId == cartItemId }.quantity = newQuantity

        _cartList.apply {
            postValue(value)
        }
    }

    suspend fun minusQuantity(cartItemId: String) {
        val newQuantity = cartList.value!!.first { it.cartItemId == cartItemId }.quantity - 1

        cartRepository
            .updateQuantity(cartItemId, newQuantity)
            .onSuccess {
                updateCartItemQuantity(cartItemId, newQuantity)
            }
            .onFailure {
                handleException(it)
            }
    }
}