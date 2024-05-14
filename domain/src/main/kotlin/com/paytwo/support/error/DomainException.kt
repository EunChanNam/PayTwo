package com.paytwo.support.error

class DomainException(
    override val message: String,
    val statusCode: Int
) : RuntimeException(message)
