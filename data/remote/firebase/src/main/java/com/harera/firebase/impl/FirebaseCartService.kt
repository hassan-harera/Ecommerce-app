package com.harera.firebase.impl

import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.harera.firebase.abstraction.CartService
import com.harera.firebase.abstraction.DBConstants
import com.harera.firebase.abstraction.DBConstants.CART
import com.harera.model.model.CartItem
import com.harera.model.model.WishItem
import javax.inject.Inject

class FirebaseCartService @Inject constructor(
    private val fStore: FirebaseFirestore,
) : CartService {

    override suspend fun addCartItem(cartItem: CartItem): Boolean =
        fStore
            .collection(CART)
            .document()
            .apply {
                cartItem.cartItemId = id
            }
            .set(cartItem)
            .let {
                Tasks.await(it)
                it.isSuccessful
            }

    override suspend fun removeCartItem(cartItemId: String): Boolean =
        fStore
            .collection(CART)
            .whereEqualTo(CartItem::cartItemId.name, cartItemId)
            .get()
            .let {
                Tasks.await(it).documents.first().id
            }
            .let { documentId ->
                fStore
                    .collection(CART)
                    .document(documentId)
                    .delete()
                    .let {
                        Tasks.await(it)
                        it.isSuccessful
                    }
            }

    override suspend fun updateQuantity(cartItemId: String, quantity: Int): Boolean =
        fStore
            .collection(CART)
            .document(cartItemId)
            .update(CartItem::quantity.name, quantity)
            .let {
                Tasks.await(it)
                it.isSuccessful
            }

    override suspend fun updateItemUid(cartItemId: String, uid: String): Boolean =
        fStore
            .collection(CART)
            .document(cartItemId)
            .update(CartItem::uid.name, uid)
            .let {
                Tasks.await(it)
                it.isSuccessful
            }

    override suspend fun getCartItem(cartItemId: String) : CartItem? =
        fStore
            .collection(CART)
            .document(cartItemId)
            .get()
            .let {
                Tasks.await(it).toObject(CartItem::class.java)
            }

    override suspend fun checkCartItem(cartItemId: String): Boolean =
        fStore
            .collection(CART)
            .document(cartItemId)
            .get()
            .let {
                Tasks.await(it).exists()
            }

    override suspend fun getUserCartItems(uid: String): List<CartItem> =
        fStore
            .collection(CART)
            .whereEqualTo(CartItem::uid.name, uid)
            .get()
            .let {
                Tasks.await(it).documents.map { it.toObject(CartItem::class.java)!! }
            }

    override suspend fun getCartItem(productId: String, uid: String): CartItem? =
        fStore
            .collection(CART)
            .whereEqualTo(CartItem::productId.name, productId)
            .whereEqualTo(CartItem::uid.name, uid)
            .get()
            .let {
                Tasks.await(it).firstOrNull()?.toObject(CartItem::class.java)
            }
}