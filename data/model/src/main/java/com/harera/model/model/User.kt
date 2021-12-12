package com.harera.model.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity
class User {
    lateinit var phoneNumber: String
    lateinit var firstName: String
    lateinit var lastName: String
    var imageUrl: String? = null
    var long: Double = 0.0
    var lat: Double = 0.0
    @PrimaryKey
    lateinit var uid: String

    constructor()

    constructor(
        phoneNumber: String,
        firstName: String,
        lastName: String,
        imageUrl: String?,
        long: Double,
        lat: Double,
    ) {
        this.phoneNumber = phoneNumber
        this.firstName = firstName
        this.lastName = lastName
        this.imageUrl = imageUrl
        this.long = long
        this.lat = lat
    }
}