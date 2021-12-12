package com.harera.manger.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.harera.common.base.BaseViewModel
import com.harera.common.local.UserDataStore
import com.harera.common.utils.Response
import com.harera.ecommerce.local.LocalDataSource
import com.harera.model.model.Category
import com.harera.model.model.Product
import com.harera.repository.abstraction.CategoryRepository
import com.harera.repository.abstraction.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.harera.model.model.Category as CategorySet

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository,
    private val marketDao: LocalDataSource,
    userDataStore: UserDataStore
) : BaseViewModel(userDataStore) {

    private val _categories: MutableLiveData<List<Category>> = MutableLiveData()
    val categories: LiveData<List<Category>> = _categories

    private val _products: MutableLiveData<List<Product>> = MutableLiveData()
    val products: LiveData<List<Product>> = _products

    private val _categoryName = MutableLiveData<String?>(null)
    val categoryName: LiveData<String?> = _categoryName

    suspend fun getCategories() {
        updateLoading(true)
        categoryRepository
            .getCategories(true)
            .onSuccess {
                updateLoading(false)
                updateCategories(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    fun addCategory(category: CategorySet) = liveData {
        categoryRepository
            .addCategory(category)
            .onSuccess {
                emit(Response.success(data = null))
            }.onFailure {
                handleException(it)
            }
    }

    private fun updateProducts(products: List<Product>) {
        _products.postValue(products)
    }

    private fun updateCategories(categories: List<Category>) {
        viewModelScope.launch(Dispatchers.Main) {
            _categories.value = categories
        }
    }

    fun setCategoryName(category: String) {
        _categoryName.value = category
    }

    suspend fun getProducts() {
        if (_categoryName.value != null)
            getCategoryProducts(_categoryName.value!!)
        else
            getAllProducts()
    }

    private suspend fun getCategoryProducts(category: String) {
        categoryRepository
            .getCategoryProducts(category)
            .onSuccess {
                _products.postValue(it)
            }
            .onFailure {
                handleException(it)
            }
    }

    private suspend fun getAllProducts() {
        updateLoading(true)
        productRepository
            .getProducts(20)
            .onSuccess {
                updateProducts(it)
                cacheProducts(it)
            }
            .onFailure {
                handleException(it)
            }
    }

    private fun cacheProducts(list: List<Product>) {
        kotlin.runCatching {
            marketDao.insertProducts(list)
        }
    }
}