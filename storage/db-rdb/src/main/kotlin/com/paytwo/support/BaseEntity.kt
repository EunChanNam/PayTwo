package com.paytwo.support

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseEntity(

    @CreatedDate
    @Column(updatable = false)
    val createdAt: LocalDateTime? = null
)
