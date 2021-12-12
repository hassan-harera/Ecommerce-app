package com.harera.repository.abstraction

import android.graphics.Bitmap
import com.harera.model.model.Category
import com.harera.model.model.Product

interface CategoryRepository {

    suspend fun getCategoryProducts(categoryName: String): Result<List<Product>>
    suspend fun getCategories(fetchOnline: Boolean = true): Result<List<Category>>
    suspend fun addCategory(category: Category): Result<Boolean>
    suspend fun uploadCategoryImage(categoryName: String, imageBitmap: Bitmap): Result<String>
}