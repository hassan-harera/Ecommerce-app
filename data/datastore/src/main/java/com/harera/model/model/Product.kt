package com.harera.model.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity
class Product {
    @PrimaryKey
    lateinit var productId: String
    lateinit var productPictureUrls: List<String>
    lateinit var categoryName: String
    lateinit var title: String
    var price: Float = 0.0f
    var amount: Float = 0.0f
    lateinit var unit: String


    constructor()

    constructor(
        productId: String,
        categoryName: String,
        title: String,
        price: Float,
        amount: Float,
        unit: String,
    ) {
        this.productId = productId
        this.categoryName = categoryName
        this.title = title
        this.price = price
        this.amount = amount
        this.unit = unit
    }
}