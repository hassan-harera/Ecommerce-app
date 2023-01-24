package com.harera.firebase.impl

import android.graphics.Bitmap
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.harera.common.utils.BitmapUtils.Companion.convertImageBitmapToByteArray
import com.harera.firebase.abstraction.DBConstants.PRODUCTS
import com.harera.firebase.abstraction.DBConstants.PRODUCT_IMAGES
import com.harera.firebase.abstraction.ProductService
import com.harera.model.model.Product
import com.harera.model.model.ProductImage
import javax.inject.Inject

class FirebaseProductService @Inject constructor(
    private val fStore: FirebaseFirestore,
    private val fStorage: FirebaseStorage,
) : ProductService {

    override suspend fun addProduct(product: Product): Boolean =
        fStore
            .collection(PRODUCTS)
            .document()
            .apply {
                product.productId = id
            }
            .set(product)
            .let {
                Tasks.await(it)
                it.isSuccessful
            }

    override suspend fun getProduct(productId: String): Product =
        fStore
            .collection(PRODUCTS)
            .document(productId)
            .get()
            .let {
                Tasks.await(it).toObject(Product::class.java)!!
            }

    override suspend fun uploadProductImage(productId: String, imageBitmap: Bitmap): String =
        fStorage
            .reference
            .child(PRODUCTS)
            .child(productId)
            .putBytes(convertImageBitmapToByteArray(imageBitmap))
            .let {
                Tasks.await(it).storage.downloadUrl.let { Tasks.await(it).toString() }
            }


    override suspend fun getProducts(limit: Int): List<Product> =
        fStore
            .collection(PRODUCTS)
            .orderBy(Product::title.name)
            .limitToLast(limit.toLong())
            .get()
            .let {
                Tasks.await(it).documents.map { it.toObject(Product::class.java)!! }
            }

    override suspend fun addImageToProduct(productId: String, productImageUrl: String): Boolean =
        fStore
            .collection(PRODUCT_IMAGES)
            .document()
            .set(ProductImage(productId, productImageUrl))
            .let {
                Tasks.await(it)
                it.isSuccessful
            }

    override suspend fun getProductImages(productId: String): List<ProductImage> =
        fStore
            .collection(PRODUCT_IMAGES)
            .whereEqualTo(Product::productId.name, productId)
            .get()
            .let {
                Tasks.await(it).documents.map { it.toObject(ProductImage::class.java)!! }
            }
}
