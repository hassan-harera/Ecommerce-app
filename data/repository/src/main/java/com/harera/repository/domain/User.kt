package com.harera.repository.domain

data class User(
    var name: String,
    var phoneNumber: String,
    var address: String,
    var uid: String,
    val firstName: String,
    val lastName: String,
    val imageUrl: String?
)