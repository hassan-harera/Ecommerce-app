package com.harera.account_form

data class CustomerFormState(
    val firstNameError: Int? = null,
    val lastNameError: Int? = null,
    val phoneNumberError: Int? = null,
    val addressError: Int? = null,
    val isValid: Boolean? = null,
)
