plugins {
    java
    application
    kotlin("jvm") version "1.3.41"
    id("org.jlleitschuh.gradle.ktlint") version "8.1.0"
}

group = "com.kraktun"
version = "0.0.1"

val coroutinesVersion = "1.3.0-M2"
val kotlinVersion = "1.3.41"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

application {
    mainClassName = "${project.group}.servilio.MainKt"
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testCompile("junit:junit:4.12")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
}

ktlint {
    verbose.set(true)
    outputToConsole.set(true)
    coloredOutput.set(true)
}