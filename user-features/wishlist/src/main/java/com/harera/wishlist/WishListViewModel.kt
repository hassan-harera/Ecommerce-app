package com.harera.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.harera.common.base.BaseViewModel
import com.harera.common.local.UserDataStore
import com.harera.model.model.CartItem
import com.harera.model.model.WishItem
import com.harera.repository.abstraction.CartRepository
import com.harera.repository.abstraction.ProductRepository
import com.harera.repository.abstraction.UserRepository
import com.harera.repository.abstraction.WishListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import javax.inject.Inject

@HiltViewModel
class WishListViewModel @Inject constructor(
    private val wishListRepository: WishListRepository,
    private val cartRepository: CartRepository,
    userDataStore: UserDataStore
) : BaseViewModel(userDataStore) {

    private val _productId = MutableLiveData<String>()

    private val _wishList = MutableLiveData<List<WishItem>>()
    val wishList: LiveData<List<WishItem>> = _wishList

    suspend fun getWishListItems() {
        updateLoading(true)

        wishListRepository
            .getUserWishItems(uid)
            .onSuccess {
                updateLoading(false)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    fun addWishItemToCart(productId: String) {
        viewModelScope.launch {
            updateLoading(true)
            removeWishItem(productId = productId)
            addCartItem(productId = productId)
            updateLoading(false)
        }
    }

    private suspend fun addCartItem(productId: String) {
        val product = wishList.value?.filter { it.productId == productId }?.first()!!

        cartRepository.addCartItem(
            CartItem(
                uid,
                productId,
                quantity = 1,
                time = DateTime.now().toString(),
                product.productImageUrl,
                product.productTitle,
                product.productPrice,
            )
        )
    }

    suspend fun removeWishItem(productId: String) {
        updateLoading(true)
        wishListRepository
            .removeWishListItem(productId,)
            .onSuccess {
                updateLoading(false)
                updateListAfterRemove(productId)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    private fun updateListAfterRemove(productId: String) {
        _wishList.apply {
            value?.dropWhile {
                it.productId == productId
            }?.let {
                postValue(it)
            }
        }
    }
}