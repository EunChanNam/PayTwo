dependencies {
    // Module
    implementation(project(":domain"))
    runtimeOnly(project(":storage:db-rdb"))
    runtimeOnly(project(":storage:db-nosql"))
}

kotlin {
    jvmToolchain(17)
}
