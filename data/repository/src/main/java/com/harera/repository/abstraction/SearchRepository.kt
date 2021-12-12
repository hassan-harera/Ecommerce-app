package com.harera.repository.abstraction

import com.harera.model.model.Product

interface SearchRepository {

    suspend fun searchProducts(text: String): Result<List<Product>>
}