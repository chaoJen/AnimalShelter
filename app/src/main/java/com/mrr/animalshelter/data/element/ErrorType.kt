package com.mrr.animalshelter.data.element

sealed class ErrorType {
    class ApiException(throwable: Throwable?) : ErrorType()
    object ApiFail : ErrorType()
}