package com.harera.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.harera.common.base.BaseViewModel
import com.harera.local.MarketDao
import com.harera.repository.CategoryRepository
import com.harera.repository.OfferRepository
import com.harera.repository.ProductRepository
import com.harera.repository.domain.Category
import com.harera.repository.domain.Offer
import com.harera.repository.domain.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val offerRepository: OfferRepository,
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val marketDao: MarketDao,
) : BaseViewModel() {

    private val page = MutableLiveData(1)
    private val PAGE_SIZE = 20

    private val _products = MutableLiveData<List<Product>>(emptyList())
    val products: LiveData<List<Product>> = _products

    private val _categories: MutableLiveData<List<Category>> = MutableLiveData(emptyList())
    val categories: LiveData<List<Category>> = _categories

    private val _offers: MutableLiveData<List<Offer>> = MutableLiveData(emptyList())
    val offers: LiveData<List<Offer>> = _offers


    fun getCategories() = viewModelScope.launch {
        updateLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository
                .getCategories(true)
                .onSuccess { categories ->
                    updateLoading(false)
                    updateCategories(categories)
                    saveInDatabase(categories)
                }
                .onFailure {
                    updateException(it)
                }
        }
    }

    private fun saveInDatabase(categories: List<Category>) = viewModelScope.launch {
        categories.forEach {
            categoryRepository.addCategory(it)
        }
    }

    fun getProducts() {
//        updateLoading(true)
//        viewModelScope.launch(Dispatchers.IO) {
//            val task = productRepository.getProducts(page.value!! * PAGE_SIZE)
//            val result = Tasks.await(task)
//
//            updateLoading(false)
//
//            if (task.isSuccessful) {
//                result.documents.map {
//                    it.toObject(Product::class.java)!!
//                }.let {
//                    updateProducts(it)
//                    cacheProducts(it)
//                }
//            } else {
//                updateException(task.exception)
//            }
//        }
    }

    private fun cacheProducts(list: List<Product>) = viewModelScope.launch {
        list.forEach {
            productRepository.insertProduct(it, true)
        }
    }

    fun getOffers() {
//        updateLoading(true)
//        viewModelScope.launch(Dispatchers.IO) {
//            val task = offerRepository.getOffers()
//            val result = Tasks.await(task)
//
//            updateLoading(false)
//
//            if (task.isSuccessful) {
//                result.documents.map {
//                    it.toObject(Offer::class.java)!!
//                }.let {
//                    updateOffers(it)
//                }
//            } else {
//                updateException(task.exception)
//            }
//        }
    }

    private fun updateProducts(products: List<Product>) {
        _products.postValue(products)
    }

    private fun updateCategories(categories: List<Category>) {
        _categories.postValue(categories)
    }

    fun nextPage() {
        page.postValue(page.value!! + 1)
        getProducts()
    }

    private fun updateOffers(offers: List<Offer>) {
        _offers.postValue(offers)
    }
}
