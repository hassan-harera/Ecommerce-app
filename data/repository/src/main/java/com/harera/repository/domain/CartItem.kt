package com.harera.repository.domain

import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class CartItem(
    var uid: String,
    var productId: String,
    var cartItemId: String,
    var quantity: Int,
    var time: Timestamp,
    var productImageUrl: String,
    var productTitle: String,
    var productPrice: Float
)
