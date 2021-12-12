package com.harera.firebase

import com.google.android.gms.tasks.Tasks
import com.google.firebase.Timestamp
import com.harera.firebase.abstraction.AuthenticationService
import com.harera.firebase.abstraction.CartService
import com.harera.model.model.CartItem
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.random.Random

@HiltAndroidTest
class FirebaseCartRepositoryTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var carRepository: CartService

    @Inject
    lateinit var authManager: AuthenticationService

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun testAddCartItem() {
        val task = carRepository
            .addCartItem(
                CartItem(
                    uid = "mibMg1y2zSSQgZHCoj9oFde6oiU2",
                    productId = "lp85r7lRC4oYPStvBMb3",
                    time = Timestamp.now(),
                    quantity = Random.nextInt(5) + 1
                )
            )
        Tasks.await(task)
        Assert.assertTrue(task.isSuccessful)
    }

    @Test
    fun testRemoveICartItem() {
        val task = carRepository
            .removeCartItem(
                cartItemId = "HnYFLstRzxJUBf4T62bI",
                uid = uid
            )
        Tasks.await(task)
        Assert.assertTrue(task.isSuccessful)
    }

    @Test
    fun testUpdateQuantity() {
        val task = carRepository
            .updateQuantity(
                cartItemId = "yVBmE1h0kfr0wcSip49b",
                quantity = 5
            )
        Tasks.await(task)
        Assert.assertTrue(task.isSuccessful)
    }

    @Test
    fun testUpdateUid() {
        val task = carRepository
            .updateItemUid(
                itemId = "yVBmE1h0kfr0wcSip49b",
                uid = "DH9WgqJyMSdGEJvMlygtZDspOjP2"
            )
        Tasks.await(task)
        Assert.assertTrue(task.isSuccessful)
    }

    @Test
    fun testGetCartItems() {
        val task = carRepository
            .getUserCartItems(
                uid = "kReJCbQzGZVJ5pefHqjIePfTIxC2"
            )
        val result = Tasks.await(task)
        Assert.assertTrue(task.isSuccessful)
        Assert.assertEquals(1, result.documents.size)
    }
}