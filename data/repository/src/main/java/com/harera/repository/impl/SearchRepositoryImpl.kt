package com.harera.repository.impl

import com.harera.firebase.abstraction.SearchService
import com.harera.model.model.Product
import com.harera.repository.abstraction.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchService: SearchService,
) : SearchRepository {

    override suspend fun searchProducts(text: String): Result<List<Product>> = kotlin.runCatching {
        searchService.searchProducts(text)
    }
}
