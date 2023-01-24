package com.harera.local.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class ProductEntity {
    @Ignore
    lateinit var productPictureUrls: List<String>
    @PrimaryKey
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
