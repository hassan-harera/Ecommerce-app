package com.harera.repository.impl

import android.graphics.Bitmap
import com.harera.firebase.abstraction.ProductService
import com.harera.model.model.Product
import com.harera.repository.abstraction.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService,
) : ProductRepository {

    override suspend fun addProduct(product: Product): Result<Boolean> = kotlin.runCatching {
        productService.addProduct(product)
    }

    override suspend fun getProduct(productId: String): Result<Product> = kotlin.runCatching {
        productService.getProduct(productId)
    }

    override suspend fun uploadProductImage(
        productId: String,
        imageBitmap: Bitmap,
    ): Result<String> = kotlin.runCatching {
        productService.uploadProductImage(productId, imageBitmap)
    }

    override suspend fun getProducts(limit: Int): Result<List<Product>> = kotlin.runCatching {
        productService.getProducts(limit)
    }

    override suspend fun uploadProductImage(
        productId: String,
        productImageUrl: String,
    ): Result<Boolean> =
        kotlin.runCatching {
            productService.addImageToProduct(productId, productImageUrl)
        }
}
