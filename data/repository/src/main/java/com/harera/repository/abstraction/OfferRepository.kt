package com.harera.repository.abstraction

import android.graphics.Bitmap
import com.harera.model.model.Offer
import com.harera.model.model.OffersGroup

interface OfferRepository {

    suspend fun getOffer(offerId: String): Result<Offer>
    suspend fun getOfferTypes(): Result<List<OffersGroup>>
    suspend fun addOfferType(offersGroup: OffersGroup): Result<Boolean>
    suspend fun getOffers(offersGroup: String): Result<List<Offer>>
    suspend fun addOffer(offer: Offer): Result<Boolean>
    suspend fun uploadOffersGroupImage(offersGroupName: String, imageBitmap: Bitmap): Result<String>
    suspend fun setOffersGroup(offersGroup: OffersGroup): Result<Boolean>
}