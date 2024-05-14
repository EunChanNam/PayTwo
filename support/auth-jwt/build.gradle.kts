val jwtVersion = "0.11.5"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:$jwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:$jwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jwtVersion")
}

kotlin {
    jvmToolchain(17)
}

tasks {
    bootJar { enabled = false }
    jar { enabled = true }
}
