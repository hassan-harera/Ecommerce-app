package com.harera.firebase

import android.graphics.Bitmap
import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.harera.firebase.abstraction.CategoryService
import com.harera.model.model.Category
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class FirebaseCategoryRepositoryTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var categoryRepository: CategoryService

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testGetProducts() {
        categoryRepository
            .getCategoryProducts("خضروات")
            .let { task ->
                Tasks.await(task).let {
                    Assert.assertEquals(it.documents.size, 6)
                }
            }
    }

    @Test
    fun testGetCategories() {
        categoryRepository
            .getCategories(true)
            .onSuccess {
                Assert.assertEquals(it.size, 1)
            }
    }

    @Test
    fun testAddCategory() {
        val task = categoryRepository
            .addCategory(
                Category(
                    categoryName = "خضروات",
                    categoryImagerUrl = "https://firebasestorage.googleapis.com/v0/b/ecommerce-55b58.appspot.com/o/categories%2F1.jpg?alt=media&token=f7a982ac-1c59-4eef-bb2a-1f872d7e9957"
                )
            )

        val result = Tasks.await(task)
        Assert.assertTrue(task.isSuccessful)
        Assert.assertNull(task.exception)
    }

    @Test
    fun uploadCategoryImage() {
        val task = categoryRepository
            .uploadCategoryImage(
                "خضروات",
                Bitmap.createBitmap(512, 512, Bitmap.Config.ALPHA_8)
        )
        val result = Tasks.await(task)

        val task2 = result.storage.downloadUrl
        val result2 = Tasks.await(task2)

        Log.d(
            "uploadCategoryImage: ", result2.toString()
        )
    }
}