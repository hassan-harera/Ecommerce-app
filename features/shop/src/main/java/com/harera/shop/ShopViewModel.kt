package com.harera.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.harera.common.base.BaseViewModel
import com.harera.common.local.UserDataStore
import com.harera.ecommerce.local.LocalDataSource
import com.harera.model.model.Category
import com.harera.model.model.Offer
import com.harera.model.model.Product
import com.harera.repository.abstraction.CategoryRepository
import com.harera.repository.abstraction.OfferRepository
import com.harera.repository.abstraction.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val offerRepository: OfferRepository,
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val marketDao: LocalDataSource,
    userDataStore: UserDataStore
) : BaseViewModel(userDataStore) {

    private val page = MutableLiveData(1)
    private val PAGE_SIZE = 20

    private val _products = MutableLiveData<List<Product>>(emptyList())
    val products: LiveData<List<Product>> = _products

    private val _categories: MutableLiveData<List<Category>> = MutableLiveData(emptyList())
    val categories: LiveData<List<Category>> = _categories

    private val _offers: MutableLiveData<List<Offer>> = MutableLiveData(emptyList())
    val offers: LiveData<List<Offer>> = _offers

    private val _offersGroup: MutableLiveData<String> = MutableLiveData("")
    val offersGroup: LiveData<String> = _offersGroup


    suspend fun getCategories() {
        updateLoading(true)
        categoryRepository
            .getCategories(true)
            .onSuccess { categories ->
                updateLoading(false)
                updateCategories(categories)
                saveInDatabase(categories)
            }
            .onFailure {
                handleException(it)
            }
    }

    private fun saveInDatabase(categories: List<Category>) {
        marketDao.insertCategory(categories)
    }

    suspend fun getProducts() {
        updateLoading(true)
        productRepository
            .getProducts(page.value!! * PAGE_SIZE)
            .onSuccess {
                updateLoading(false)
                updateProducts(it)
                cacheProducts(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    private fun cacheProducts(list: List<Product>) {
        kotlin.runCatching {
            marketDao.insertProducts(list)
        }
    }

    suspend fun getOffers() {
        updateLoading(true)
        offerRepository
            .getOffers(offersGroup = offersGroup.value!!)
            .onSuccess {
                updateLoading(false)
                updateOffers(it)
            }
            .onFailure {
                handleException(it)
            }
    }

    private fun updateProducts(products: List<Product>) {
        _products.postValue(products)
    }

    private fun updateCategories(categories: List<Category>) {
        _categories.postValue(categories)
    }

    suspend fun nextPage() {
        page.postValue(page.value!! + 1)
        getProducts()
    }

    private fun updateOffers(offers: List<Offer>) {
        _offers.postValue(offers)
    }
}
