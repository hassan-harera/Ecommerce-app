package com.harera.firebase

import com.google.android.gms.tasks.Tasks
import com.google.firebase.Timestamp
import com.harera.firebase.abstraction.OfferService
import com.harera.model.model.Offer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class FirebaseOfferRepositoryTest  {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var offerRepository: OfferService

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun testGetOffers() {
        val task = offerRepository
            .getAllOffers("خصم 25%")

        Tasks.await(task).let {
            it.documents.map {
                it.toObject(Offer::class.java)!!
            }.let {
                TestCase.assertEquals(3, it.size)
            }
        }
    }

    @Test
    fun testGetOfferTypes() {
        val task = offerRepository
            .getOfferTypes()

        Tasks.await(task).let {
            it.children.map {
                it.getValue(String::class.java)!!
            }.let {
                TestCase.assertEquals(2, it.size)
            }
        }
    }

    @Test
    fun testAddOfferType() {
        val task =  offerRepository
            .addOfferType("خصم 50%")

        Tasks.await(task).let {
            TestCase.assertEquals(task.isSuccessful, true)
        }
    }

    @Test
    fun testAddOffer() {
        val task =  offerRepository
            .addOffer(
                Offer(
                    productId = "brx94M1zVzRuMftoqrv7",
                    startTime = Timestamp.now(),
                    endTime = Timestamp(Timestamp.now().seconds + 100000, 0),
                    offerImageUrl = "https://png.pngtree.com/png-clipart/20200226/original/pngtree-red-big-sale-50-discount-for-your-product-png-image_5318657.jpg",
                    offerTypeId = "Mij3H4k5PA4XYLesLNX",
                    offerTitle = "خصم 50%"
                )
            )

        Tasks.await(task).let {
            TestCase.assertEquals(task.isSuccessful, true)
        }
    }

    fun testGetOfferById() {}
}