package com.harera.model.model

import com.google.firebase.Timestamp

class OfferType {
    lateinit var startTime: Timestamp
    lateinit var endTime: Timestamp
    lateinit var offerTitle: String
    lateinit var offerTypeId: String
    lateinit var offerImageUrl: String


    constructor(
        startTime: Timestamp,
        endTime: Timestamp,
        offerTitle: String,
        offerTypeId: String,
        offerImageUrl: String,
    ) {
        this.startTime = startTime
        this.endTime = endTime
        this.offerTitle = offerTitle
        this.offerTypeId = offerTypeId
        this.offerImageUrl = offerImageUrl
    }

    constructor()
}