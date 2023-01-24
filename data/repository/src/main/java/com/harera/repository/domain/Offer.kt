package com.harera.repository.domain

import java.util.*


class Offer(
    var categoryName: String,
    var offerType: String,
    var productId: String,
    var offerId: String,
    var offerTitle: String,
    var offerImageUrl: String,
    var startTime: Date,
    var endTime: Date,
    var type: Int?
)