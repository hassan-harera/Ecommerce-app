package com.harera.model.model

class ProductImage {
    lateinit var productId : String
    lateinit var imageUrl : String

    constructor(productId: String, imageUrl: String) {
        this.productId = productId
        this.imageUrl = imageUrl
    }

    constructor()
}