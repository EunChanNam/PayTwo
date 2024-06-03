package com.paytwo.support

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.utility.DockerImageName

abstract class TestContainerSupport protected constructor() {

    companion object {

        private const val REDIS_IMAGE = "redis:latest"
        private const val MYSQL_IMAGE = "mysql:8.0"
        private const val REDIS_PORT = 6379
        private const val MYSQL_PORT = 3306

        private val REDIS = GenericContainer(DockerImageName.parse(REDIS_IMAGE))
            .withReuse(true)
            .withExposedPorts(REDIS_PORT)

        private val MYSQL: MySQLContainer<*> = MySQLContainer(DockerImageName.parse(MYSQL_IMAGE))
            .withReuse(true)
            .withExposedPorts(MYSQL_PORT)

        init {
            REDIS.start()
            MYSQL.start()
        }

        @JvmStatic
        @DynamicPropertySource
        fun overrideProps(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.redis.host") { REDIS.host }
            registry.add("spring.data.redis.port") { REDIS.getMappedPort(REDIS_PORT).toString() }

            registry.add("spring.datasource.driver-class-name") { MYSQL.driverClassName }
            registry.add("spring.datasource.url") { MYSQL.jdbcUrl }
            registry.add("spring.datasource.username") { MYSQL.username }
            registry.add("spring.datasource.password") { MYSQL.password }
        }
    }
}
