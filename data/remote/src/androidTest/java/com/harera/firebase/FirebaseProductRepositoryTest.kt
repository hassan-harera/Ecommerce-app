package com.harera.firebase

import android.graphics.Bitmap
import com.google.android.gms.tasks.Tasks
import com.harera.firebase.abstraction.ProductService
import com.harera.model.model.Product
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.random.Random

@HiltAndroidTest
class FirebaseProductRepositoryTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var productRepository: ProductService

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testAddProduct() {
        DummyData.vegetableImages.forEachIndexed { id, it ->
            val task = productRepository
                .addProduct(
                    Product(
                        title = DummyData.vegetableNames[id],
                        amount = Random.nextInt(30).toDouble(),
                        categoryName = "خضروات",
                        price = Random.nextInt(500).toDouble(),
                        productPictureUrls = arrayListOf(
                            it
                        ),
                        unit = "كيلو",
                    )
                )
            Tasks.await(task)
        }
    }

    @Test
    fun testUploadProductImage() {
        val imageBitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.ALPHA_8)

        val task = productRepository
            .uploadProductImage(
                productId = "HfkcpLR4cRI4w0fyUd2N",
                imageBitmap = imageBitmap
            )

        Tasks.await(task).let {
            Assert.assertNull(task.exception)
            Assert.assertTrue(task.isSuccessful)
        }
    }

    @Test
    fun testGetProduct() {
        val task = productRepository.getProduct("HfkcpLR4cRI4w0fyUd2N")
        Tasks.await(task).let {
            Assert.assertNull(task.exception)
            Assert.assertEquals(it.id, "HfkcpLR4cRI4w0fyUd2N")
        }
    }
}