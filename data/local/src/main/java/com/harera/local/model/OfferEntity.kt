package com.harera.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class OfferEntity {
    lateinit var categoryName: String
    lateinit var offerType: String
    lateinit var productId: String

    @PrimaryKey
    lateinit var offerId: String
    lateinit var offerTitle: String
    lateinit var offerImageUrl: String
    lateinit var startTime: Date
    lateinit var endTime: Date
    var type: Int? = null

    constructor()

    constructor(
        categoryName: String,
        offerType: String,
        productId: String,
        offerId: String,
        offerTitle: String,
        offerImageUrl: String,
        startTime: Date,
        endTime: Date,
        type: Int?
    ) {
        this.categoryName = categoryName
        this.offerType = offerType
        this.productId = productId
        this.offerId = offerId
        this.offerTitle = offerTitle
        this.offerImageUrl = offerImageUrl
        this.startTime = startTime
        this.endTime = endTime
        this.type = type
    }
}