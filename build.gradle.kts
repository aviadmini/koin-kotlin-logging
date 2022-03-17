plugins {
    kotlin("jvm") version "1.6.10"
    `maven-publish`
}

group = "pw.avi"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    val koinVersion = "3.2.0-beta-1"
    implementation("io.insert-koin:koin-core:$koinVersion")
    val kotlinLoggingVersion = "2.1.21"
    implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingVersion")
}