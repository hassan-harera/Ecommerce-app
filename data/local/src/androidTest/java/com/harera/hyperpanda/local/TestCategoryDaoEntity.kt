package com.harera.hyperpanda.local

import com.google.common.truth.Truth
import com.harera.local.MarketDao
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class TestCategoryDaoEntity {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    private lateinit var dao: MarketDao

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun getCategories() {
        dao.getCategories().let {
            Truth.assertThat(it.size).isEqualTo(5)
        }
    }
}