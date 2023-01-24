package com.harera.firebase.abstraction

import com.harera.model.model.CartItem

interface CartService {

    suspend fun addCartItem(cartItem: CartItem): Boolean
    suspend fun removeCartItem(cartItemId: String): Boolean
    suspend fun updateQuantity(cartItemId: String, quantity: Int): Boolean
    suspend fun getUserCartItems(uid: String): List<CartItem>
    suspend fun updateItemUid(cartItemId: String, uid: String): Boolean
    suspend fun getCartItem(cartItemId: String): CartItem?
    suspend fun checkCartItem(productId: String, uid: String): Boolean
    suspend fun getCartItem(productId: String, uid: String): CartItem?
}