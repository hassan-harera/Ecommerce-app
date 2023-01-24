package com.harera.service.impl

import android.graphics.Bitmap
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.harera.service.DBConstants.PRODUCTS
import com.harera.service.ProductService
import com.harera.service.domain.ServiceDomainProduct
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class ProductServiceImpl @Inject constructor(
    private val fStore: FirebaseFirestore,
    private val fStorage: FirebaseStorage,
) : ProductService {

    override fun addProduct(product: ServiceDomainProduct): Boolean =
        fStore
            .collection(PRODUCTS)
            .document()
            .apply {
                product.productId = id
            }
            .set(product)
            .isSuccessful

    override fun getProduct(productId: String): ServiceDomainProduct? =
        fStore
            .collection(PRODUCTS)
            .document(productId)
            .get()
            .result
            ?.toObject(ServiceDomainProduct::class.java)

    override fun uploadProductImage(productId: String, imageBitmap: Bitmap): Boolean {
        val inputStream = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, inputStream)

        return fStorage
            .reference
            .child(PRODUCTS)
            .child(productId)
            .putBytes(inputStream.toByteArray())
            .isSuccessful
    }

    override fun getProducts(limit: Int): List<ServiceDomainProduct> =
        fStore
            .collection(PRODUCTS)
            .orderBy(ServiceDomainProduct::title.name)
            .limitToLast(limit.toLong())
            .get()
            .result
            ?.map {
                it.toObject(ServiceDomainProduct::class.java)
            }
            ?: emptyList()

    override fun updateProductImage(productId: String, productImageUrl: String): Boolean =
        fStore
            .collection(PRODUCTS)
            .document(productId)
            .update(ServiceDomainProduct::productPictureUrls.name, arrayListOf(productImageUrl))
            .isSuccessful
}