package com.harera.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.harera.common.base.BaseViewModel
import com.harera.repository.*
import com.harera.repository.domain.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val wishListRepository: WishListRepository,
    private val categoryRepository: CategoryRepository,
    private val authManager: UserRepository,
) : BaseViewModel() {

    private val _productId = MutableLiveData<String>()

    private val _wishState = MutableLiveData<Boolean>()
    val wishState: LiveData<Boolean> = _wishState

    private val _cartState = MutableLiveData<Boolean>()
    val cartState: LiveData<Boolean> = _cartState

    private val _products: MutableLiveData<List<Product>> = MutableLiveData()
    val products: LiveData<List<Product>> = _products

    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> = _product

    fun getProduct() = viewModelScope.launch {
        updateLoading(true)

        productRepository
            .getProduct(_productId.value!!)
            .asLiveData()
    }

    fun setProductId(productId: String) {
        _productId.value = productId
    }

    fun getCartState() = viewModelScope.launch(Dispatchers.IO) {
//        cartRepository
//            .getCartItem(_productId.value!!, authManager.getCurrentUser()!!.uid)
//            .onSuccess {
//                updateCartState(it)
//            }
//            .onFailure {
//                handleException(it)
//            }
    }

    private fun updateCartState(state: Boolean) = viewModelScope.launch(Dispatchers.Main) {
        _cartState.value = state
    }

    fun getWishState() = viewModelScope.launch(Dispatchers.IO) {
//        wishListRepository
//            .getWishItem(
//                _productId.value!!,
//                authManager.getCurrentUser()!!.uid
//            )
//            .onSuccess {
//                updateWishState(it)
//            }
//            .onFailure {
//                handleException(it)
//            }
    }

    private fun updateWishState(state: Boolean) = viewModelScope.launch(Dispatchers.Main) {
        _wishState.value = state
    }

    fun changeCartState() = viewModelScope.launch(Dispatchers.IO) {
        this@ProductViewModel.getCartState().join()

        if (cartState.value!!) {
            removeCartItem()
        } else {
            addCartItem()
        }
    }

    fun changeWishState() = viewModelScope.launch(Dispatchers.IO) {
        this@ProductViewModel.getWishState().join()

        if (wishState.value!!) {
            removeWishItem()
        } else {
            addWishItem()
        }
    }

    private fun removeWishItem() = viewModelScope.launch(Dispatchers.IO) {
//        wishListRepository.removeWishListItem(
//            _productId.value!!,
//            authManager.getCurrentUser()!!.uid
//        ).onSuccess {
//            updateWishState(false)
//        }.onFailure {
//            handleException(it)
//        }
    }

    private fun addWishItem() = viewModelScope.launch(Dispatchers.IO) {
//        val item = WishItem(
//            _productId.value!!,
//            authManager.getCurrentUser()!!.uid,
//            Timestamp.now()
//        )
//
//        wishListRepository
//            .addWishListItem(item)
//            .onSuccess {
//                updateWishState(true)
//            }
//            .onFailure {
//                handleException(it)
//            }
    }

    private fun addCartItem() = viewModelScope.launch(Dispatchers.IO) {
//        val item = CartItem(
//            _productId.value!!,
//            authManager.getCurrentUser()!!.uid,
//            1,
//            Timestamp.now(),
//        )
//
//        cartRepository
//            .addCartItem(item)
//            .onSuccess {
//                updateCartState(true)
//            }
//            .onFailure {
//                handleException(it)
//            }
    }

    private fun removeCartItem() = viewModelScope.launch(Dispatchers.IO) {
//        cartRepository
//            .removeCartItem(_productId.value!!, authManager.getCurrentUser()!!.uid)
//            .onSuccess {
//                updateCartState(false)
//            }
//            .onFailure {
//                handleException(it)
//            }
    }

    fun getCategoryProducts(category: String) {
//        categoryRepository
//            .getCategoryProducts(category)
//            .onSuccess {
//                _products.postValue(it)
//            }
//            .onFailure {
//                handleException(it)
//            }
    }
}