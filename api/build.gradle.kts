val swaggerVersion = "2.1.0"

dependencies {
    // DB
    runtimeOnly("com.h2database:h2")

    // Module
    runtimeOnly(project(":storage:db-rdb"))
    runtimeOnly(project(":storage:db-nosql"))
    runtimeOnly(project(":support:common-dependency"))
    implementation(project(":domain"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$swaggerVersion")
}

kotlin {
    jvmToolchain(17)
}
