package com.harera.service

import android.graphics.Bitmap
import com.harera.service.domain.ServiceDomainProduct

interface ProductService {

    fun addProduct(product: ServiceDomainProduct): Boolean
    fun getProduct(productId: String): ServiceDomainProduct?
    fun uploadProductImage(productId: String, imageBitmap: Bitmap): Boolean
    fun getProducts(limit: Int): List<ServiceDomainProduct>
    fun updateProductImage(productId: String, productImageUrl: String): Boolean
}