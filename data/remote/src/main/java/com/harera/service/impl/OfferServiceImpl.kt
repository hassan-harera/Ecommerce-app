package com.harera.service.impl

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.harera.service.DBConstants.OFFERS
import com.harera.service.DBConstants.OFFERS_CATEGORIES
import com.harera.service.OfferService
import com.harera.service.domain.ServiceDomainOffer
import javax.inject.Inject

class OfferServiceImpl @Inject constructor(
    private val fStore: FirebaseFirestore,
    private val fDatabase: FirebaseDatabase,
) : OfferService {

    override fun getOffers(offerCategory: String): List<ServiceDomainOffer> =
        fStore
            .collection(OFFERS)
            .whereEqualTo(ServiceDomainOffer::offerTitle.name, offerCategory)
            .get()
            .result!!
            .map {
                it.toObject(ServiceDomainOffer::class.java)
            }

    override fun insertOffer(serviceDomainOffer: ServiceDomainOffer): Boolean =
        fStore
            .collection(OFFERS)
            .document()
            .apply {
                serviceDomainOffer.offerId = id
            }
            .set(serviceDomainOffer)
            .isSuccessful

    override fun getOfferCategories(): List<String> =
        fDatabase
            .reference
            .child(OFFERS_CATEGORIES)
            .get()
            .result!!
            .getValue(List::class.java) as List<String>

    override fun insertOfferCategory(offerCategory: String): Boolean =
        fDatabase
            .reference
            .child(OFFERS)
            .push()
            .setValue(offerCategory)
            .isSuccessful

    override fun getOffer(offerId: String): ServiceDomainOffer? =
        fStore.collection(OFFERS)
            .document(offerId)
            .get()
            .result!!
            .toObject(ServiceDomainOffer::class.java)

    override fun getOffers(): List<ServiceDomainOffer> =
        fStore
            .collection(OFFERS)
            .get()
            .result!!
            .map {
                it.toObject(ServiceDomainOffer::class.java)
            }
}

