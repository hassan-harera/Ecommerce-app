package com.harera.firebase.abstraction

import android.graphics.Bitmap
import com.google.firebase.storage.UploadTask
import com.harera.model.model.Category
import com.harera.model.model.Product

interface CategoryService {

    suspend fun getCategoryProducts(categoryName: String): List<Product>
    suspend fun getCategories(): List<Category>
    suspend fun addCategory(category: Category): Boolean
    suspend fun uploadCategoryImage(categoryName: String, imageBitmap: Bitmap): String
}