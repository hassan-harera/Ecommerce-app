package com.harera.repository.mapper

import com.harera.local.model.OfferEntity
import com.harera.repository.domain.Offer
import com.harera.service.domain.ServiceDomainOffer

object OfferMapper {

    fun toOffer(offer: ServiceDomainOffer): Offer {
        return Offer(
            categoryName = offer.categoryName,
            offerType = offer.offerType,
            productId = offer.productId,
            offerId = offer.offerId,
            offerTitle = offer.offerTitle,
            offerImageUrl = offer.offerImageUrl,
            startTime = offer.startTime.toDate(),
            endTime = offer.endTime.toDate(),
            type = offer.type
        )
    }

    fun toOffer(offer: OfferEntity): Offer {
        return Offer(
            categoryName = offer.categoryName,
            offerType = offer.offerType,
            productId = offer.productId,
            offerId = offer.offerId,
            offerTitle = offer.offerTitle,
            offerImageUrl = offer.offerImageUrl,
            startTime = offer.startTime,
            endTime = offer.endTime,
            type = offer.type
        )
    }

    fun toOfferEntity(offer: Offer): OfferEntity {
        return OfferEntity(
            categoryName = offer.categoryName,
            offerType = offer.offerType,
            productId = offer.productId,
            offerId = offer.offerId,
            offerTitle = offer.offerTitle,
            offerImageUrl = offer.offerImageUrl,
            startTime = offer.startTime,
            endTime = offer.endTime,
            type = offer.type
        )
    }
}