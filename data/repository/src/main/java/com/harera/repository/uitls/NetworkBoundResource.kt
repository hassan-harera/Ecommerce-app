package com.harera.repository.uitls

import kotlinx.coroutines.flow.flow


inline fun <LocalType, RemoteType> networkBoundResource(
    crossinline query: suspend () -> LocalType,
    crossinline fetch: suspend () -> RemoteType,
    crossinline saveFetchResult: suspend (RemoteType) -> Unit,
    crossinline shouldFetch: (LocalType) -> Boolean = { true }
) = flow<Resource<LocalType>> {
    try {
        val data = query()

        if (data == null) {
            emit(Resource.Loading())
            saveFetchResult(fetch())
            emit(Resource.Success(query()))
        } else {
            if (shouldFetch(data)) {
                emit(Resource.Loading(data))
                saveFetchResult(fetch())
                emit(Resource.Success(query()))
            } else {
                emit(Resource.Success(data))
            }
        }
    } catch (exception: Exception) {
        emit(Resource.Error(exception))
    }
}