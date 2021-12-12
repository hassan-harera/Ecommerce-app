package com.harera.repository.impl

import android.graphics.Bitmap
import com.harera.firebase.abstraction.OfferService
import com.harera.model.model.Offer
import com.harera.model.model.OffersGroup
import com.harera.repository.abstraction.OfferRepository
import javax.inject.Inject

class OfferRepositoryImpl @Inject constructor(
    private val firebaseOfferService: OfferService,
) : OfferRepository {

    override suspend fun addOffer(offer: Offer): Result<Boolean> = kotlin.runCatching {
        firebaseOfferService.addOffer(offer)
    }

    override suspend fun getOfferTypes(): Result<List<OffersGroup>> = kotlin.runCatching {
        firebaseOfferService.getOfferTypes()
    }

    override suspend fun addOfferType(offersGroup: OffersGroup): Result<Boolean> =
        kotlin.runCatching {
            firebaseOfferService.addOfferType(offersGroup)
        }

    override suspend fun getOffer(offerId: String): Result<Offer> = kotlin.runCatching {
        firebaseOfferService.getOffer(offerId)
    }

    override suspend fun getOffers(offersGroup: String): Result<List<Offer>> = kotlin.runCatching {
        firebaseOfferService.getAllOffers(offersGroup)
    }

    override suspend fun uploadOffersGroupImage(
        offersGroupName: String,
        imageBitmap: Bitmap,
    ): Result<String> = kotlin.runCatching {
        firebaseOfferService.uploadOffersGroupImage(offersGroupName, imageBitmap)
    }

    override suspend fun setOffersGroup(offersGroup: OffersGroup): Result<Boolean> =
        kotlin.runCatching {
            firebaseOfferService.setOffersGroup(offersGroup)
        }
}

