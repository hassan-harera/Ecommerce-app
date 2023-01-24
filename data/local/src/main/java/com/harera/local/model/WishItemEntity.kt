package com.harera.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class WishItemEntity {
    lateinit var uid: String
    lateinit var productId: String

    @PrimaryKey
    var itemId: String = uid + productId
    lateinit var time: Date
    lateinit var productImageUrl: String
    lateinit var productTitle: String
}
