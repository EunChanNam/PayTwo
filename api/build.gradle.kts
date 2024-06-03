val swaggerVersion = "2.1.0"
val testContainerVersion = "1.19.3"

dependencies {
    // Module
    runtimeOnly(project(":storage:db-rdb"))
    runtimeOnly(project(":storage:db-nosql"))
    runtimeOnly(project(":support:common-dependency"))
    implementation(project(":domain"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$swaggerVersion")

    testImplementation("org.testcontainers:testcontainers:$testContainerVersion")
    testImplementation("org.testcontainers:mysql:$testContainerVersion")
}

kotlin {
    jvmToolchain(17)
}
