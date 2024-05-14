package com.paytwo.advice

import com.paytwo.support.error.DomainException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiControllerAdvice {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(DomainException::class)
    fun handleException(e: DomainException): ResponseEntity<ErrorResponse> {
        log.error("DomainException : {}", e.message, e)
        return ResponseEntity(ErrorResponse(e.message), HttpStatus.valueOf(e.statusCode))
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        log.error("Exception : {}", e.message, e)
        return ResponseEntity(ErrorResponse("서버 오류입니다."), HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
