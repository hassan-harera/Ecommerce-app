package com.harera.repository

import com.harera.model.model.Product

interface SearchRepository {

    suspend fun searchProducts(text: String): Result<List<Product>>
}