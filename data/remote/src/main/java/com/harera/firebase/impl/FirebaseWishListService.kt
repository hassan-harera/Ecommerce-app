package com.harera.firebase.impl

import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.harera.firebase.abstraction.DBConstants.WISHLIST
import com.harera.firebase.abstraction.WishService
import com.harera.model.model.CartItem
import com.harera.model.model.WishItem
import javax.inject.Inject

class FirebaseWishListService @Inject constructor(
    private val fStore: FirebaseFirestore,
) : WishService {

    override suspend fun addWishListItem(wishListItem: WishItem): Boolean =
        fStore
            .collection(WISHLIST)
            .document()
            .apply {
                wishListItem.wishItemId = id
            }
            .set(wishListItem)
            .let {
                Tasks.await(it)
                it.isSuccessful
            }

    override suspend fun removeWishListItem(wishItemId: String): Boolean =
        fStore
            .collection(WISHLIST)
            .document(wishItemId)
            .delete()
            .let {
                Tasks.await(it)
                it.isSuccessful
            }

    override suspend fun getUserWishItems(uid: String): List<WishItem> =
        fStore
            .collection(WISHLIST)
            .whereEqualTo(CartItem::uid.name, uid)
            .get()
            .let {
                Tasks.await(it).documents.map { it.toObject(WishItem::class.java)!! }
            }

    override suspend fun updateItemUid(wishItemId: String, uid: String): Boolean =
        fStore
            .collection(WISHLIST)
            .document(wishItemId)
            .update(CartItem::uid.name, uid)
            .let {
                Tasks.await(it)
                it.isSuccessful
            }

    override suspend fun checkWishItem(wishItemId: String): Boolean =
        fStore
            .collection(WISHLIST)
            .document(wishItemId)
            .get()
            .let {
                Tasks.await(it).exists()
            }

    override suspend fun getWishItem(productId: String, uid: String): WishItem? =
        fStore
            .collection(WISHLIST)
            .whereEqualTo(WishItem::productId.name, productId)
            .whereEqualTo(WishItem::uid.name, uid)
            .get()
            .let {
                Tasks.await(it).firstOrNull()?.toObject(WishItem::class.java)
            }
}