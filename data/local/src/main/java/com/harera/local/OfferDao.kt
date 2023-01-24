package com.harera.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.harera.local.model.OfferEntity

@Dao
interface OfferDao {

    @Query("select * from OfferEntity where offerId = :offerId")
    suspend fun getOffer(offerId: String): OfferEntity

    @Query("select * from OfferEntity")
    suspend fun getOffers(): List<OfferEntity>
    
    @Insert
    suspend fun insertOffer(offer: OfferEntity)
}