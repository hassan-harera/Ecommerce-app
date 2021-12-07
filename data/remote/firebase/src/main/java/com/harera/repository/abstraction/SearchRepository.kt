package com.harera.repository.abstraction

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

interface SearchRepository {

    fun searchProducts(text: String): Task<QuerySnapshot>
}