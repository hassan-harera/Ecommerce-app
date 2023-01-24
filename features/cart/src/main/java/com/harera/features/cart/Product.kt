package com.harera.features.cart

data class Product(
    var productPictureUrls: List<String>,
    var productId: String,
    var categoryName: String,
    var title: String,
    var price: Double,
    var amount: Double,
    var unit: String
)