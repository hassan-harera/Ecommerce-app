package com.harera.firebase.abstraction

import android.graphics.Bitmap
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.UploadTask
import com.harera.model.model.Product
import com.harera.model.model.ProductImage

interface ProductService {

    suspend fun addProduct(product: Product): Boolean
    suspend fun getProduct(productId: String): Product
    suspend fun uploadProductImage(productId: String, imageBitmap: Bitmap): String
    suspend fun getProducts(limit: Int): List<Product>
    suspend fun addImageToProduct(productId: String, productImageUrl: String): Boolean
    suspend fun getProductImages(productId: String) : List<ProductImage>
}