package com.harera.repository.impl

import com.harera.firebase.impl.FirebaseWishListService
import com.harera.model.model.WishItem
import com.harera.repository.abstraction.WishListRepository
import javax.inject.Inject

class WishListRepositoryImpl @Inject constructor(
    private val firebaseWishListRepository: FirebaseWishListService,
) : WishListRepository {

    override suspend fun addWishListItem(wishListItem: WishItem): Result<Boolean> =
        kotlin.runCatching {
            firebaseWishListRepository.addWishListItem(wishListItem)
        }

    override suspend fun removeWishListItem(wishItemId: String,): Result<Boolean> =
        kotlin.runCatching {
            firebaseWishListRepository.removeWishListItem(wishItemId)
        }

    override suspend fun getUserWishItems(
        uid: String,
        forceRefresh: Boolean,
    ): Result<List<WishItem>> = kotlin.runCatching {
        if (forceRefresh)
            firebaseWishListRepository.getUserWishItems(uid)
        else
            emptyList()
    }

    override suspend fun updateItemUid(wishItemId: String, uid: String): Result<Boolean> =
        kotlin.runCatching {
            firebaseWishListRepository.updateItemUid(wishItemId, uid)
        }

    override suspend fun checkWishItem(productId: String, uid: String): Result<Boolean> =
        kotlin.runCatching {
            firebaseWishListRepository.checkWishItem(uid)
        }

    override suspend fun getWishItem(productId: String, uid: String): Result<WishItem?> =
        kotlin.runCatching {
            firebaseWishListRepository.getWishItem(productId, uid)
        }
}