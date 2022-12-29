plugins {
    kotlin("multiplatform") version "1.7.20"
    id("convention.publication")
}

group = "pw.avi"
version = "2.0.0"

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    js(IR)
    @Suppress("UNUSED_VARIABLE")
    sourceSets {
        val commonMain by getting {
            dependencies {
                compileOnly("io.insert-koin:koin-core:3.3.2")
                compileOnly("io.github.microutils:kotlin-logging:3.0.4")
            }
        }
    }
}