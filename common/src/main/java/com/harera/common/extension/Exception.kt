package com.harera.common.extension


fun Throwable.toException(): Exception {
    return Exception(this)
}