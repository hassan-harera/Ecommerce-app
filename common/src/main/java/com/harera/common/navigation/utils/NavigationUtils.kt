package com.harera.common.navigation.utils

object NavigationUtils {

    fun getUriNavigation(domain: String, destination: String, argument: String?): String {
        return "$domain://$destination/$argument"
    }
}