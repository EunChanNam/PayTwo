package com.paytwo

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["com.paytwo"])
class PaytwoApiApplication {

    fun main(args: Array<String>) {
        System.setProperty("spring.config.name", "auth-jwt")
        SpringApplication.run(PaytwoApiApplication::class.java, *args)
    }
}
