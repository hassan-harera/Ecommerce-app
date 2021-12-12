package com.harera.firebase.abstraction

import com.harera.model.model.Product

interface SearchService {

    suspend fun searchProducts(text: String): List<Product>
}