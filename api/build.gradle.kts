val swaggerVersion = "2.1.0"

dependencies {
    // Module
    implementation(project(":domain"))
    runtimeOnly(project(":storage:db-rdb"))
    runtimeOnly(project(":storage:db-nosql"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$swaggerVersion")

    // testFixtures
    //    testImplementation(testFixtures(project(":infra")))
}

kotlin {
    jvmToolchain(17)
}
