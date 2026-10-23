package com.altoservicios.centros.domain.util

fun <T, R> Resource<T>.map(transform: (T) -> R): Resource<R> = when (this) {
    is Resource.Success -> Resource.Success(transform(data))
    is Resource.Failure -> Resource.Failure(message)
    Resource.Loading -> Resource.Loading
}
