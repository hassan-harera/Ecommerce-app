package com.harera.firebase.abstraction

import android.graphics.Bitmap
import com.google.android.gms.tasks.Task
import com.harera.model.model.Offer
import com.harera.model.model.OffersGroup

interface OfferService {

    suspend fun getOffer(offerId: String): Offer
    suspend fun getOfferTypes(): List<OffersGroup>
    suspend fun addOfferType(offerType: OffersGroup): Boolean
    suspend fun getAllOffers(offerType: String): List<Offer>
    suspend fun addOffer(offer: Offer): Boolean
    suspend fun getAllOffers(): List<Offer>
    suspend fun uploadOffersGroupImage(offersGroupName: String, imageBitmap: Bitmap): String
    suspend fun setOffersGroup(offersGroup: OffersGroup) : Boolean
}