package com.harera.repository.abstraction

import android.graphics.Bitmap
import com.harera.model.model.Product

interface ProductRepository {

    suspend fun addProduct(product: Product): Result<Boolean>
    suspend fun getProduct(productId: String): Result<Product>
    suspend fun uploadProductImage(productId: String, imageBitmap: Bitmap): Result<String>
    suspend fun getProducts(limit: Int): Result<List<Product>>
    suspend fun uploadProductImage(productId: String, productImageUrl: String): Result<Boolean>
}