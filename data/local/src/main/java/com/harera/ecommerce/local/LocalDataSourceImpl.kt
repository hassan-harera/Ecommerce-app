package com.harera.ecommerce.local

import com.harera.model.model.CartItem
import com.harera.model.model.Category
import com.harera.model.model.Product

class LocalDataSourceImpl(
) : LocalDataSource {
    override fun insertCategory(list: List<Category>) {
//        TODO("Not yet implemented")
    }

    override fun insertCategory(list: Category) {
        //        TODO("Not yet implemented")
    }

    override fun insertProducts(list: List<Product>) {
        //        TODO("Not yet implemented")
    }

    override fun getCategories(): List<Category> {
//        TODO("Not yet implemented")
        return emptyList()
    }

    override fun addCartItem(cartItem: CartItem) {
//        TODO("Not yet implemented")
    }
}