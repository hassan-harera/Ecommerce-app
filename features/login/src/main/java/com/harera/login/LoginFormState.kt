package com.harera.login

data class LoginFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isValid: Boolean? = null,
)
