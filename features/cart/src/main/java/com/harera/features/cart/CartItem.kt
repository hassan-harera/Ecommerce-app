package com.harera.features.cart

import java.util.*

data class CartItem(
    var uid: String,
    var productId: String,
    var cartItemId: String,
    var quantity: Int,
    var time: Date,
    var productImageUrl: String,
    var productTitle: String,
    var productPrice: Float
)
