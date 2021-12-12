package com.harera.firebase.impl

import android.graphics.Bitmap
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.harera.common.utils.BitmapUtils
import com.harera.model.model.Product
import com.harera.firebase.abstraction.CategoryService
import com.harera.firebase.abstraction.DBConstants
import com.harera.firebase.abstraction.ProductService
import javax.inject.Inject
import com.harera.model.model.Category
import com.harera.model.model.ProductImage

class FirebaseCategoryService @Inject constructor(
    private val fStore: FirebaseFirestore,
    private val fStorage: FirebaseStorage,
    private val productService: ProductService,
) : CategoryService {

    override suspend fun getCategoryProducts(categoryName: String): List<Product> =
        fStore
            .collection(DBConstants.PRODUCTS)
            .whereEqualTo(Product::categoryName.name, categoryName)
            .get()
            .let {
                Tasks.await(it).documents.map { it.toObject(Product::class.java)!! }
            }

    override suspend fun addCategory(category: Category): Boolean =
        fStore
            .collection(DBConstants.CATEGORIES)
            .document(category.categoryName)
            .set(category)
            .let {
                Tasks.await(it)
                it.isSuccessful
            }

    override suspend fun uploadCategoryImage(categoryName: String, imageBitmap: Bitmap): String =
        fStorage
            .reference
            .child(DBConstants.CATEGORIES)
            .child(categoryName)
            .putBytes(BitmapUtils.convertImageBitmapToByteArray(imageBitmap))
            .let {
                Tasks.await(it).storage.downloadUrl.let { Tasks.await(it).toString() }
            }


    override suspend fun getCategories(): List<Category> =
        fStore
            .collection(DBConstants.CATEGORIES)
            .get()
            .let {
                Tasks.await(it).documents.map { it.toObject(Category::class.java)!! }
            }
}