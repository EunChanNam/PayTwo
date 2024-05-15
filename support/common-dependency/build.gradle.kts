dependencies {
    compileOnly("org.springframework.boot:spring-boot-starter")
}

kotlin {
    jvmToolchain(17)
}

tasks {
    bootJar { enabled = false }
    jar { enabled = true }
}
