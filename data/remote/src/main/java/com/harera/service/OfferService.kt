package com.harera.service

import com.harera.service.domain.ServiceDomainOffer

interface OfferService {

    fun getOfferCategories(): List<String>
    fun getOffer(offerId: String): ServiceDomainOffer?
    fun insertOfferCategory(offerCategory: String): Boolean
    fun insertOffer(serviceDomainOffer: ServiceDomainOffer): Boolean
    fun getOffers(): List<ServiceDomainOffer>
    fun getOffers(offerCategory: String): List<ServiceDomainOffer>
}