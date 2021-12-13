package com.harera.repository.abstraction

import com.harera.model.model.CartItem

interface CartRepository {

    suspend fun removeCartItem(cartItemId: String, forceRefresh: Boolean = true): Result<Boolean>

    suspend fun updateQuantity(
        cartItemId: String,
        quantity: Int,
        forceRefresh: Boolean = true,
    ): Result<Boolean>

    suspend fun getUserCartItems(
        uid: String,
        forceRefresh: Boolean = true,
    ): Result<List<CartItem>>

    suspend fun addCartItem(cartItem: CartItem, forceRefresh: Boolean = true): Result<Boolean>

    suspend fun updateItemUid(
        cartItemId: String,
        uid: String,
        forceRefresh: Boolean = true,
    ): Result<Boolean>

    suspend fun getCartItem(
        cartItemId: String,
        forceRefresh: Boolean = true,
    ): Result<CartItem?>

    suspend fun checkCart(productId: String, uid: String,): Result<Boolean>

    suspend fun removeCartItem(cartItemId: String): Result<Boolean>
    suspend fun getCartItem(productId: String, uid: String): Result<CartItem?>
}