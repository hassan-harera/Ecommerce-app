package com.harera.model.model

import androidx.room.Entity
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity
class WishItem {
    lateinit var productId: String
    lateinit var uid: String
    lateinit var wishItemId: String
    lateinit var time: String
    lateinit var productImageUrl: String
    lateinit var productTitle: String
    var productPrice: Float = 0.0f

    constructor()

    constructor(
        productId: String,
        uid: String,
        wishItemId: String,
        time: String,
        productImageUrl: String,
        productTitle: String,
        productPrice: Float,
    ) {
        this.productId = productId
        this.uid = uid
        this.wishItemId = wishItemId
        this.time = time
        this.productImageUrl = productImageUrl
        this.productTitle = productTitle
        this.productPrice = productPrice
    }

    constructor(
        productId: String,
        uid: String,
        time: String,
        productImageUrl: String,
        productTitle: String,
        productPrice: Float,
    ) {
        this.productId = productId
        this.uid = uid
        this.time = time
        this.productImageUrl = productImageUrl
        this.productTitle = productTitle
        this.productPrice = productPrice
    }
}
