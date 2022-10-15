plugins {
    kotlin("jvm") version "1.7.20"
    `maven-publish`
}

group = "pw.avi"
version = "1.2.0"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(kotlin("stdlib"))
    val koinVersion = "3.2.2"
    compileOnly("io.insert-koin:koin-core:$koinVersion")
    val kotlinLoggingVersion = "3.0.0"
    compileOnly("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}