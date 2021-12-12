package com.harera.repository.impl

import android.graphics.Bitmap
import com.harera.firebase.abstraction.CategoryService
import com.harera.ecommerce.local.LocalDataSource
import com.harera.model.model.Category
import com.harera.model.model.Product
import com.harera.repository.abstraction.CategoryRepository
import javax.inject.Inject
import com.harera.model.model.Category as CategoryGet

class CategoryRepositoryImpl @Inject constructor(
    private val marketDao: LocalDataSource,
    private val categoryService: CategoryService,
) : CategoryRepository {

    override suspend fun getCategoryProducts(categoryName: String): Result<List<Product>> =
        kotlin.runCatching {
            categoryService.getCategoryProducts(categoryName)
        }

    override suspend fun addCategory(category: Category): Result<Boolean> = kotlin.runCatching {
        categoryService.addCategory(category)
    }

    override suspend fun uploadCategoryImage(
        categoryName: String,
        imageBitmap: Bitmap,
    ): Result<String> =
        kotlin.runCatching {
            categoryService.uploadCategoryImage(categoryName, imageBitmap)
        }

    override suspend fun getCategories(fetchOnline: Boolean): Result<List<CategoryGet>> =
        kotlin.runCatching {
            if (fetchOnline) {
                categoryService.getCategories()
            } else {
                marketDao.getCategories()
            }
        }
}