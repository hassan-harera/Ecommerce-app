package com.harera.service.domain

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
class ServiceDomainUser {
    lateinit var name: String
    lateinit var phoneNumber: String
    lateinit var address: String
    lateinit var uid: String
}