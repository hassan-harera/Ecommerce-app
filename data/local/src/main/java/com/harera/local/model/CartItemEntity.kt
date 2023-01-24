package com.harera.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity()
class CartItemEntity {

    lateinit var uid: String
    lateinit var productId: String
    @PrimaryKey
    lateinit var cartItemId: String
    var quantity: Int = -1
    lateinit var time: Date
    lateinit var productImageUrl: String
    lateinit var productTitle: String
    var productPrice: Float = -1f

    constructor()

    constructor(
        uid: String,
        productId: String,
        cartItemId: String,
        quantity: Int,
        time: Date,
        productImageUrl: String,
        productTitle: String,
        productPrice: Float
    ) {
        this.uid = uid
        this.productId = productId
        this.cartItemId = cartItemId
        this.quantity = quantity
        this.time = time
        this.productImageUrl = productImageUrl
        this.productTitle = productTitle
        this.productPrice = productPrice
    }
}