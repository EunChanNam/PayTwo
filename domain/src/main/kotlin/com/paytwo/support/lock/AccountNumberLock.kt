package com.paytwo.support.lock

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class AccountNumberLock(
    val accountNumberCount: Int
)
