dependencies {
    compileOnly("org.springframework.boot:spring-boot-starter")

    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
}

kotlin {
    jvmToolchain(17)
}

tasks {
    bootJar { enabled = false }
    jar { enabled = true }
}
