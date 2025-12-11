plugins {
    kotlin("jvm") version "2.0.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.arrow-kt:arrow-core:1.2.4")
    implementation("io.arrow-kt:arrow-fx-coroutines:1.2.4")
    implementation("io.arrow-kt:arrow-cache4k:1.2.4")
    implementation("org.jetbrains.kotlinx:multik-core:0.2.3")
    implementation("org.jetbrains.kotlinx:multik-default:0.2.3")
    implementation("com.google.ortools:ortools-java:9.12.4544")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}