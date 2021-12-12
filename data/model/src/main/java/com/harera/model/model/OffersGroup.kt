package com.harera.model.model

class OffersGroup {
    lateinit var offersGroupName: String
    lateinit var imagerUrl: String

    constructor()

    constructor(offersGroupName: String, imagerUrl: String) {
        this.offersGroupName = offersGroupName
        this.imagerUrl = imagerUrl
    }
}