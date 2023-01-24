package com.harera.firebase.abstraction

import com.harera.model.model.WishItem

interface WishService {

    suspend fun addWishListItem(wishListItem: WishItem): Boolean
    suspend fun removeWishListItem(wishItemId: String): Boolean
    suspend fun getUserWishItems(uid: String): List<WishItem>
    suspend fun updateItemUid(wishItemId: String, uid: String): Boolean
    suspend fun checkWishItem(wishItemId: String): Boolean
    suspend fun getWishItem(productId: String, uid: String): WishItem?
}