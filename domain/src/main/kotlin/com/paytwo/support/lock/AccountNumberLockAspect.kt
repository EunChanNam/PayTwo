package com.paytwo.support.lock

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Aspect
@Order(1)
@Component
class AccountNumberLockAspect(
    private val lockProvider: LockProvider
) {

    private val logger = LoggerFactory.getLogger(AccountNumberLock::class.java)

    @Around("@annotation(com.paytwo.support.lock.AccountNumberLock)")
    fun runWithDoubleAccountNumberLock(proceedingJoinPoint: ProceedingJoinPoint): Any? {
        // 메서드 시그니처 가져오기
        val methodSignature = proceedingJoinPoint.signature as MethodSignature
        val method = methodSignature.method
        val annotation = method.getAnnotation(AccountNumberLock::class.java)

        // 어노테이션 정보 가져오기
        val accountNumberCount = annotation.accountNumberCount

        // 메서드 파라미터 가져오기
        val methodArgs = proceedingJoinPoint.args

        // 어노테이션에 가져온 계좌번호 개수만큼 락을 취득할 계좌번호를 파라미터에서 추출
        val accountNumbers = (0 until accountNumberCount)
            .map { methodArgs[it] as Long }
            .map { it.toString() }
            .toList()

        // 스핀락으로 락 취득
        while (!lockProvider.multiLock(accountNumbers)) {
            Thread.sleep(10)
        }

        return try {
            // 실제 비즈니스 로직 수행
            proceedingJoinPoint.proceed()
        } finally {
            // 락 반환 후 종료
            logger.info("release lock")
            lockProvider.multiUnlock(accountNumbers)
        }
    }
}
