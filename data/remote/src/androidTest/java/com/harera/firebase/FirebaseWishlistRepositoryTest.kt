package com.harera.firebase

import com.google.android.gms.tasks.Tasks
import com.google.firebase.Timestamp
import com.harera.firebase.abstraction.AuthenticationService
import com.harera.firebase.abstraction.WishService
import com.harera.model.model.WishItem
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class FirebaseWishlistRepositoryTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var wishListRepository: WishService

    @Inject
    lateinit var authManager : AuthenticationService

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun testAddCartItem() {
        val task = wishListRepository
            .addWishListItem(
                WishItem(
                    "mibMg1y2zSSQgZHCoj9oFde6oiU2",
                    "lp85r7lRC4oYPStvBMb3",
                    Timestamp.now(),
                )
            )
        Tasks.await(task)
        Assert.assertTrue(task.isSuccessful)
    }

    @Test
    fun testRemoveWishListItem() {
        val task = wishListRepository
            .removeWishListItem(
                wishItemId = String
            )
        Tasks.await(task)
        Assert.assertTrue(task.isSuccessful)
    }

    @Test
    fun testUpdateUid() {
        val task = wishListRepository
            .updateItemUid(
                itemId = "GtctIjSzijUso5hAkEwr",
                uid = "DH9WgqJyMSdGEJvMlygtZDspOjP2"
            )
        Tasks.await(task)
        Assert.assertTrue(task.isSuccessful)
    }

    @Test
    fun testGetCartItems() {
        val task = wishListRepository
            .getUserWishItems(
                uid = "mibMg1y2zSSQgZHCoj9oFde6oiU2"
            )
        val result = Tasks.await(task)
        Assert.assertTrue(task.isSuccessful)
        Assert.assertEquals(1, result.documents.size)
    }
}