package com.harera.service.domain

import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
class ServiceDomainCartItem {

    lateinit var uid: String
    var productId: String
    lateinit var cartItemId: String
    var quantity: Int = -1
    lateinit var time: Timestamp
    lateinit var productImageUrl: String
    lateinit var productTitle: String
    var productPrice: Float = -1f


    constructor(
        uid: String,
        productId: String,
        cartItemId: String,
        quantity: Int,
        time: Timestamp,
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