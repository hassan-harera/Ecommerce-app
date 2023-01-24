package com.harera.model.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity
class Offer {
    lateinit var categoryName: String
    lateinit var offerType: String
    lateinit var productId: String
    @PrimaryKey
    lateinit var offerId: String
    lateinit var offerTitle: String
    lateinit var offerImageUrl: String
    lateinit var startTime: Timestamp
    lateinit var endTime: Timestamp
    var type: Int? = null


    constructor()

    constructor(
        categoryName: String,
        offerType: String,
        productId: String,
        offerId: String,
        offerTitle: String,
        offerImageUrl: String,
        startTime: Timestamp,
        endTime: Timestamp,
        type: Int?,
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