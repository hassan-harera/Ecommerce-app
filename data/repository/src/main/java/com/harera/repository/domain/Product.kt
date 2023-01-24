package com.harera.repository.domain

class Product {

    lateinit var productPictureUrls: List<String>
    lateinit var productId: String
    lateinit var categoryName: String
    lateinit var title: String
    var price: Double = 0.0
    var amount: Double = 0.0
    lateinit var unit: String

    constructor()

    constructor(
        productPictureUrls: List<String>,
        productId: String,
        categoryName: String,
        title: String,
        price: Double,
        amount: Double,
        unit: String
    ) {
        this.productPictureUrls = productPictureUrls
        this.productId = productId
        this.categoryName = categoryName
        this.title = title
        this.price = price
        this.amount = amount
        this.unit = unit
    }
}