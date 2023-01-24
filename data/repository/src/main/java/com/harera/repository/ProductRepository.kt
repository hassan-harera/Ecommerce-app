package com.harera.repository

import android.graphics.Bitmap
import com.harera.repository.domain.Product
import com.harera.repository.uitls.Resource
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun insertProduct(product: Product, forceOnline: Boolean = true): Flow<Boolean>
    fun getProduct(productId: String, forceOnline: Boolean = true): Flow<Resource<Product>>
    fun uploadProductImage(productId: String, imageBitmap: Bitmap): Flow<Boolean>
    fun getProducts(limit: Int): Flow<List<Product>>
    fun uploadProductImage(productId: String, productImageUrl: String): Result<Boolean>
    fun updateProductImage(productId: String, productImageUrl: String): Flow<Boolean>
}