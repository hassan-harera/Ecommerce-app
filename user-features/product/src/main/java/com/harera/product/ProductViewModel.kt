package com.harera.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.harera.common.base.BaseViewModel
import com.harera.common.local.UserDataStore
import com.harera.model.model.CartItem
import com.harera.model.model.Product
import com.harera.model.model.WishItem
import com.harera.repository.abstraction.CartRepository
import com.harera.repository.abstraction.CategoryRepository
import com.harera.repository.abstraction.ProductRepository
import com.harera.repository.abstraction.WishListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.joda.time.DateTime
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val wishListRepository: WishListRepository,
    private val categoryRepository: CategoryRepository,
    userDataStore: UserDataStore,
) : BaseViewModel(userDataStore) {

    private val _productId = MutableLiveData<String>()
    val productId: LiveData<String> = _productId

    private val _wishState = MutableLiveData<Boolean>()
    val wishState: LiveData<Boolean> = _wishState

    private val _cartState = MutableLiveData<Boolean>()
    val cartState: LiveData<Boolean> = _cartState

    private val _wishItem = MutableLiveData<WishItem>()
    val wishItem: LiveData<WishItem> = _wishItem

    private val _cartItem = MutableLiveData<CartItem>()
    val cartItem: LiveData<CartItem> = _cartItem

    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> = _product

    private val _products: MutableLiveData<List<Product>> = MutableLiveData()
    val products: LiveData<List<Product>> = _products

    suspend fun getProduct() {
        productRepository
            .getProduct(_productId.value!!)
            .onSuccess {
                _product.postValue(it)
                getRelatedProducts(it.categoryName)
            }
            .onFailure {
                handleException(it)
            }
    }

    fun setProductId(productId: String) {
        _productId.value = productId
    }

    suspend fun getCartState() {
        cartRepository
            .getCartItem(productId.value!!, uid)
            .onSuccess {
                updateCartState(it != null)
                _cartItem.postValue(it)
            }
            .onFailure {
                handleException(it)
            }
    }

    private fun updateCartState(state: Boolean) {
        _cartState.postValue(state)
    }

    suspend fun getWishState() {
        wishListRepository
            .getWishItem(productId.value!!, uid)
            .onSuccess {
                updateWishState(it != null)
                _wishItem.postValue(it)
            }
            .onFailure { throwable ->
                handleException(throwable)
            }
    }

    private fun updateWishState(state: Boolean) {
        _wishState.postValue(state)
    }

    suspend fun changeCartState() {
        cartRepository
            .getCartItem(productId.value!!, uid)
            .onSuccess {
                changeCartState(it)
            }
            .onFailure {
                handleException(it)
            }
    }

    private suspend fun changeCartState(cartState: CartItem?) {
        if (cartState != null) {
            removeCartItem(cartState.cartItemId)
        } else {
            addCartItem()
        }
    }

    suspend fun changeWishState() {
        wishListRepository
            .getWishItem(productId.value!!, uid)
            .onSuccess {
                changeWishState(it)
            }
            .onFailure {
                handleException(it)
            }
    }

    private suspend fun changeWishState(wishState: WishItem?) {
        if (wishState != null) {
            removeWishItem(wishState.wishItemId)
        } else {
            addWishItem()
        }
    }

    private suspend fun removeWishItem(wishItemId: String) {
        wishListRepository
            .removeWishListItem(wishItemId)
            .onSuccess {
                updateWishState(it.not())
            }.onFailure {
                handleException(it)
            }
    }

    private suspend fun addWishItem() {
        val product = product.value!!

        val item = WishItem(
            productId = product.productId,
            uid = uid,
            time = DateTime.now().toString(),
            productPrice = product.price,
            productImageUrl = product.productPictureUrls.first(),
            productTitle = product.title,
        )

        wishListRepository
            .addWishListItem(item)
            .onSuccess {
                updateWishState(it)
            }
            .onFailure {
                handleException(it)
            }
    }

    private suspend fun addCartItem() {
        val product = product.value!!

        val item = CartItem(
            productId = product.productId,
            uid = uid,
            quantity = 1,
            time = DateTime.now().toString(),
            productTitle = product.title,
            productImageUrl = product.productPictureUrls.first(),
            productPrice = product.price,
        )

        cartRepository
            .addCartItem(item)
            .onSuccess {
                updateCartState(it)
            }
            .onFailure {
                handleException(it)
            }
    }

    private suspend fun removeCartItem(cartItemId: String) {
        cartRepository
            .removeCartItem(cartItemId)
            .onSuccess {
                updateCartState(it.not())
            }
            .onFailure {
                handleException(it)
            }
    }

    private suspend fun getRelatedProducts(categoryName: String) {
        categoryRepository
            .getCategoryProducts(categoryName)
            .onSuccess {
                _products.postValue(it)
            }
            .onFailure {
                handleException(it)
            }
    }
}