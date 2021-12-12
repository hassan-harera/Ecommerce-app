package com.harera.firebase.impl

import android.graphics.Bitmap
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.harera.common.utils.BitmapUtils
import com.harera.firebase.abstraction.DBConstants.OFFERS
import com.harera.firebase.abstraction.DBConstants.OFFERS_COLLECTION
import com.harera.firebase.abstraction.OfferService
import com.harera.model.model.Offer
import com.harera.model.model.OffersGroup
import javax.inject.Inject

class FirebaseOfferService @Inject constructor(
    private val fStore: FirebaseFirestore,
    private val fDatabase: FirebaseDatabase,
    private val fStorage: FirebaseStorage,
) : OfferService {

    override suspend fun getAllOffers(offerType: String): List<Offer> =
        fStore
            .collection(OFFERS)
            .whereEqualTo(Offer::offerTitle.name, offerType)
            .get()
            .let {
                Tasks.await(it).documents.map { it.toObject(Offer::class.java)!! }
            }

    override suspend fun addOffer(offer: Offer): Boolean =
        fStore
            .collection(OFFERS)
            .document()
            .apply {
                offer.offerId = id
            }
            .set(offer)
            .let {
                Tasks.await(it)
                it.isSuccessful
            }

    override suspend fun uploadOffersGroupImage(offersGroupName: String, imageBitmap: Bitmap): String =
        fStorage
            .reference
            .child(OFFERS_COLLECTION)
            .child(offersGroupName)
            .putBytes(BitmapUtils.convertImageBitmapToByteArray(imageBitmap))
            .let { uploadTask ->
                Tasks.await(uploadTask)
                    .let { result ->
                        result.storage.downloadUrl
                            .let {
                                Tasks.await(it).toString()
                            }
                    }
            }

    override suspend fun setOffersGroup(offersGroup: OffersGroup): Boolean =
        fStore
            .collection(OFFERS_COLLECTION)
            .document()
            .set(offersGroup)
            .let { task -> Tasks.await(task).let { task.isSuccessful } }

    override suspend fun getOfferTypes(): List<OffersGroup> =
        fStore
            .collection(OFFERS_COLLECTION)
            .get()
            .let {
                Tasks.await(it).documents.map { it.toObject(OffersGroup::class.java)!! }
            }

    override suspend fun addOfferType(offerType: OffersGroup): Boolean =
        fStore
            .collection(OFFERS_COLLECTION)
            .document(offerType.offersGroupName)
            .set(offerType)
            .let {
                Tasks.await(it)
                it.isSuccessful
            }

    override suspend fun getOffer(offerId: String): Offer =
        fStore.collection(OFFERS)
            .document(offerId)
            .get()
            .let {
                Tasks.await(it).toObject(Offer::class.java)!!
            }

    override suspend fun getAllOffers(): List<Offer> =
        fStore
            .collection(OFFERS)
            .get()
            .let { Tasks.await(it).documents.map { it.toObject(Offer::class.java)!! } }
}

