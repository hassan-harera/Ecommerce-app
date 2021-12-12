package com.harera.ecommerce.local

import com.harera.model.model.CartItem
import com.harera.model.model.Category
import com.harera.model.model.Product

interface LocalDataSource {

    fun insertCategory(list: List<Category>)
    fun insertCategory(list: Category)
    fun insertProducts(list: List<Product>)
    fun getCategories(): List<Category>
    fun addCartItem(cartItem: CartItem)
}