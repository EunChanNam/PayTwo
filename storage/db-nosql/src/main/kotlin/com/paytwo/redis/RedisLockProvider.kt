package com.paytwo.redis

import com.paytwo.support.lock.LockProvider
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisLockProvider(
    private val redisTemplate: RedisTemplate<String, Any>
) : LockProvider {

    private val logger = LoggerFactory.getLogger(RedisLockProvider::class.java)

    override fun multiLock(keys: List<String>): Boolean {
        val keyValueMap = keys.associateBy({ it }, { true })
        return redisTemplate.opsForValue().multiSetIfAbsent(keyValueMap) ?: return false
    }

    override fun multiUnlock(keys: List<String>) {
        redisTemplate.delete(keys)
    }
}
