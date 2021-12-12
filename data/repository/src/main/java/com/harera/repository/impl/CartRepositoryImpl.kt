package com.harera.repository.impl

import com.harera.firebase.abstraction.CartService
import com.harera.ecommerce.local.LocalDataSource
import com.harera.model.model.CartItem
import com.harera.repository.abstraction.CartRepository
import com.harera.repository.abstraction.UserRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartService: CartService,
    private val authManager: UserRepository,
    private val databaseDao: LocalDataSource,
) : CartRepository {

    override suspend fun addCartItem(cartItem: CartItem, forceRefresh: Boolean) =
        kotlin.runCatching {
            if (forceRefresh) {
                cartService.addCartItem(cartItem)
            } else {
                true
//            TODO fix data class model for firebase
//            databaseDao.addCartItem(cartItem)
            }
        }

    override suspend fun removeCartItem(
        cartItemId: String,
        forceRefresh: Boolean,
    ): Result<Boolean> = kotlin.runCatching {
        if (forceRefresh) {
            cartService.removeCartItem(cartItemId)
        } else {
            true
            TODO()
        }
    }

    override suspend fun removeCartItem(cartItemId: String): Result<Boolean> =
        kotlin.runCatching {
            cartService.removeCartItem(cartItemId)
        }

    override suspend fun updateQuantity(
        cartItemId: String,
        quantity: Int,
        forceRefresh: Boolean,
    ): Result<Boolean> = kotlin.runCatching {
        if (forceRefresh) {
            cartService.updateQuantity(cartItemId, quantity)
        } else {
            true
            TODO()
        }
    }

    override suspend fun updateItemUid(
        cartItemId: String,
        uid: String,
        forceRefresh: Boolean,
    ): Result<Boolean> =
        kotlin.runCatching {
            if (forceRefresh) {
                cartService.updateItemUid(cartItemId, uid)
            } else {
                TODO()
            }
        }

    override suspend fun getCartItem(
        cartItemId: String,
        forceRefresh: Boolean,
    ): Result<CartItem?> =
        kotlin.runCatching {
            if (forceRefresh) {
                cartService.getCartItem(cartItemId)
            } else {
                TODO()
            }
        }

    override suspend fun checkCart(
        cartItemId: String,
    ): Result<Boolean> =
        kotlin.runCatching {
            cartService.checkCartItem(cartItemId)
        }

    override suspend fun getUserCartItems(
        uid: String,
        forceRefresh: Boolean,
    ): Result<List<CartItem>> =
        kotlin.runCatching {
            if (forceRefresh) {
                cartService.getUserCartItems(uid)
            } else {
                TODO()
            }
        }

    override suspend fun getCartItem(productId: String, uid: String): Result<CartItem?> =
        kotlin.runCatching {
            cartService.getCartItem(productId, uid)
        }
}