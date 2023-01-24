package com.harera.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.harera.repository.domain.Offer
import com.harera.repository.uitls.Resource
import kotlinx.coroutines.flow.Flow

interface OfferRepository {

    fun getOffer(offerId: String, forceOnline: Boolean = false): Flow<Resource<Offer>>
    fun getOfferCategories(): Task<DataSnapshot>
    fun insertOfferCategory(offerCategory: String): Task<Void>
    fun getOffers(offerType: String): Task<QuerySnapshot>
    fun insertOffer(offer: Offer): Task<Void>
    fun getOffers(forceOnline: Boolean): Flow<Resource<List<Offer>>>
}