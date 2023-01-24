package com.harera.firebase.impl

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.harera.model.model.Product
import com.harera.firebase.abstraction.DBConstants.PRODUCTS
import com.harera.firebase.abstraction.SearchService
import javax.inject.Inject

class FirebaseSearchService @Inject constructor(
    private val fStore: FirebaseFirestore
) : SearchService {

    override suspend fun searchProducts(text: String): List<Product> =
        fStore
            .collection(PRODUCTS)
            .whereGreaterThanOrEqualTo(Product::title.name, text)
            .get()
            .let {
                Tasks.await(it).documents.map { it.toObject(Product::class.java)!! }
            }
}
