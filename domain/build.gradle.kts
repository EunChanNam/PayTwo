val transactionVersion = "6.1.1"

dependencies {
    implementation(project(":support:auth-jwt"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-aop")

    compileOnly("org.springframework:spring-tx:$transactionVersion")
}

kotlin {
    jvmToolchain(17)
}

tasks {
    bootJar { enabled = false }
    jar { enabled = true }
}
