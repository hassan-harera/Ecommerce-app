package com.harera.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.harera.common.base.BaseViewModel
import com.harera.common.local.UserDataStore
import com.harera.model.model.Product
import com.harera.repository.abstraction.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    userDataStore: UserDataStore
) : BaseViewModel(userDataStore) {

    private val _products: MutableLiveData<List<Product>> = MutableLiveData()
    val products: LiveData<List<Product>> = _products

    suspend fun searchProducts(text: String) {
        updateLoading(true)
        searchRepository
            .searchProducts(text = text)
            .onSuccess {
                updateLoading(false)
                _products.postValue(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }
}