plugins {
    kotlin("multiplatform") version "1.7.20"
    `maven-publish`
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
                val koinVersion = "3.3.2"
                compileOnly("io.insert-koin:koin-core:$koinVersion")
                val kotlinLoggingVersion = "3.0.4"
                compileOnly("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
            }
        }
    }
}

val javadocJar = tasks.register("javadocJar", Jar::class.java) {
    archiveClassifier.set("javadoc")
}
publishing {
    publications {
        withType<MavenPublication> {
            artifact(javadocJar)
        }
    }
}