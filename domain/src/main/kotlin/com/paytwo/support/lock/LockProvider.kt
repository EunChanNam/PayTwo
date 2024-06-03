package com.paytwo.support.lock

interface LockProvider {

    fun multiLock(keys: List<String>): Boolean

    fun multiUnlock(keys: List<String>)
}
