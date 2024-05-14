plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "paytwo"
include("api")
include("domain")
include("batch")
include("storage:db-rdb")
include("storage:db-nosql")
