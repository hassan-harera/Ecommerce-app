package com.harera.model.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties


@IgnoreExtraProperties
@Entity
class Category {
    @PrimaryKey
    lateinit var categoryName: String
    lateinit var categoryImagerUrl: String

    constructor(categoryName: String, categoryImagerUrl: String) {
        this.categoryName = categoryName
        this.categoryImagerUrl = categoryImagerUrl
    }

    constructor()
}