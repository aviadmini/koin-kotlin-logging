plugins {
    kotlin("jvm") version "1.7.10"
    `maven-publish`
}

group = "pw.avi"
version = "1.1.2"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(kotlin("stdlib"))
    val koinVersion = "3.2.0"
    compileOnly("io.insert-koin:koin-core:$koinVersion")
    val kotlinLoggingVersion = "2.1.23"
    compileOnly("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingVersion")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}