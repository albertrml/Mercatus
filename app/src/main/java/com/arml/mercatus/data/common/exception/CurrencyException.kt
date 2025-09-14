package com.arml.mercatus.data.common.exception

sealed class CurrencyException(override val message: String): Exception() {
    class IllegalMathOperationException : CurrencyException(
        "The Operands must be of the same type"
    )
}