package com.harera.wish_item

import java.util.*

data class WishItem(
    var uid: String,
    var productId: String,
    var itemId: String = uid + productId,
    var time: Date,
    var productImageUrl: String,
    var productTitle: String
)
