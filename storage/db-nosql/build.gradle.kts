dependencies {
    // Module
    compileOnly(project(":domain"))

    // Spring
    compileOnly("org.springframework.boot:spring-boot-starter")
    //    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

kotlin {
    jvmToolchain(17)
}

tasks {
    bootJar { enabled = false }
    jar { enabled = true }
}
