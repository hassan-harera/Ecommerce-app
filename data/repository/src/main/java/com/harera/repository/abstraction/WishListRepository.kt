package com.harera.repository.abstraction

import com.harera.model.model.WishItem

interface WishListRepository {

    suspend fun addWishListItem(wishListItem: WishItem): Result<Boolean>
    suspend fun removeWishListItem(wishItemId: String): Result<Boolean>
    suspend fun getUserWishItems(uid: String, forceRefresh: Boolean = true): Result<List<WishItem>>
    suspend fun updateItemUid(wishItemId: String, uid: String): Result<Boolean>
    suspend fun checkWishItem(productId: String, uid: String): Result<Boolean>
    suspend fun getWishItem(productId: String, uid: String): Result<WishItem?>
}