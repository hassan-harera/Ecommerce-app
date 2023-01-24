package com.harera.model.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties
import com.harera.model.model.CartItem

@IgnoreExtraProperties
@Entity()
class CartResponse {
    lateinit var productId: String
    lateinit var uid: String
    lateinit var cartItemId: String
    lateinit var time: String
    lateinit var productImageUrl: String
    lateinit var productTitle: String
    var productPrice: Float = 0.0f
    var quantity: Float = 0.0f


    constructor()

    constructor(
        uid: String,
        productId: String,
        quantity: Float,
        time: String,
        productImageUrl: String,
        productTitle: String,
        cartItemId: String,
        productPrice: Float,
    ) {
        this.uid = uid
        this.productId = productId
        this.quantity = quantity
        this.time = time
        this.cartItemId = cartItemId
        this.productImageUrl = productImageUrl
        this.productTitle = productTitle
        this.productPrice = productPrice
    }
}