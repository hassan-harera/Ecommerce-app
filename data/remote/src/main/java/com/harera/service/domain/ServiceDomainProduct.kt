package com.harera.service.domain

data class ServiceDomainProduct(
    var productPictureUrls: List<String>,
    var productId: String,
    var categoryName: String,
    var title: String,
    var price: Double = 0.0,
    var amount: Double = 0.0,
    var unit: String
)
