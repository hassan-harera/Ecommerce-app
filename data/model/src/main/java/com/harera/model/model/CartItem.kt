package com.harera.model.model

import androidx.room.Entity
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity()
class CartItem {
    lateinit var productId: String
    lateinit var uid: String
    lateinit var cartItemId: String
    lateinit var time: String
    lateinit var productImageUrl: String
    lateinit var productTitle: String
    var productPrice: Float = 0.0f
    var quantity: Int = 0

    constructor()

    constructor(
        uid: String,
        productId: String,
        quantity: Int,
        time: String,
        productImageUrl: String,
        productTitle: String,
        productPrice: Float,
    ) {
        this.uid = uid
        this.productId = productId
        this.quantity = quantity
        this.time = time
        this.productImageUrl = productImageUrl
        this.productTitle = productTitle
        this.productPrice = productPrice
    }

    constructor(
        productId: String,
        uid: String,
        cartItemId: String,
        time: String,
        productImageUrl: String,
        productTitle: String,
        productPrice: Float,
        quantity: Int,
    ) {
        this.productId = productId
        this.uid = uid
        this.cartItemId = cartItemId
        this.time = time
        this.productImageUrl = productImageUrl
        this.productTitle = productTitle
        this.productPrice = productPrice
        this.quantity = quantity
    }
}