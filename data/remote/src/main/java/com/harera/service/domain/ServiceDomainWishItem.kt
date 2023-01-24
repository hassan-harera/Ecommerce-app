package com.harera.service.domain

import com.google.firebase.Timestamp

class ServiceDomainWishItem {
    lateinit var uid: String
    lateinit var productId: String
    var itemId : String = uid + productId
    lateinit var time: Timestamp
    lateinit var productImageUrl: String
    lateinit var productTitle: String
}
