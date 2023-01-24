package com.harera.offer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.harera.common.base.BaseViewModel
import com.harera.common.local.UserDataStore
import com.harera.model.model.CartItem
import com.harera.model.model.Offer
import com.harera.model.model.Product
import com.harera.model.model.WishItem
import com.harera.repository.abstraction.CartRepository
import com.harera.repository.abstraction.OfferRepository
import com.harera.repository.abstraction.ProductRepository
import com.harera.repository.abstraction.WishListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.joda.time.DateTime
import javax.inject.Inject

@HiltViewModel
class OfferViewModel @Inject constructor(
    private val offerRepository: OfferRepository,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val wishListRepository: WishListRepository,
    userDataStore: UserDataStore,
) : BaseViewModel(userDataStore) {

    private val _offerId = MutableLiveData<String>()

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

    private val _offer: MutableLiveData<Offer> = MutableLiveData()
    val offer: LiveData<Offer> = _offer

    private val _offers: MutableLiveData<List<Offer>> = MutableLiveData()
    val offers: LiveData<List<Offer>> = _offers

    suspend fun getOffer() {
        offerRepository
            .getOffer(_offerId.value!!)
            .onSuccess {
                getProduct()
            }
            .onFailure {
                handleException(it)
            }
    }

    private suspend fun getProduct() {
        productRepository
            .getProduct(offer.value!!.productId)
            .onSuccess {
                _product.postValue(it)
            }
            .onFailure {
                handleException(it)
            }
    }

    fun setOfferId(productId: String) {
        _offerId.value = productId
    }

    suspend fun getCartState() {
        cartRepository
            .getCartItem(product.value!!.productId, uid)
            .onSuccess {
                updateCartState(it != null)
                _cartItem.postValue(it)
            }
            .onFailure {
                handleException(it)
            }
    }

    private fun updateCartState(state: Boolean) {
        _cartState.value = state
    }

    suspend fun getWishState() {
        wishListRepository
            .getWishItem(
                product.value!!.productId,
                uid
            )
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
        if (cartState.value!!) {
            removeCartItem(cartItem.value!!.cartItemId)
        } else {
            addCartItem()
        }
    }

    suspend fun changeWishState() {
        if (wishState.value!!) {
            removeWishItem(wishItem.value!!.wishItemId)
        } else {
            addWishItem()
        }
    }

    private suspend fun removeWishItem(wishItemId: String) {
        wishListRepository
            .removeWishListItem(wishItemId)
            .onSuccess {
                updateWishState(it)
            }.onFailure {
                handleException(it)
            }
    }

    private suspend fun addWishItem() {
        val product = product.value!!

        val item = WishItem(
            productId = _offerId.value!!,
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
                updateCartState(true)
            }
            .onFailure {
                handleException(it)
            }
    }

    private suspend fun removeCartItem(cartItemId: String) {
        cartRepository
            .removeCartItem(cartItemId)
            .onSuccess {
                updateCartState(it)
            }
            .onFailure {
                handleException(it)
            }
    }

    suspend fun getRelatedOffers() {
        offerRepository
            .getOffers(offer.value!!.offerType)
            .onSuccess {
                _offers.value = it
            }
            .onFailure {
                handleException(it)
            }
    }
}