import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    application
    kotlin("jvm") version "1.3.50"
    id("org.jlleitschuh.gradle.ktlint") version "8.1.0"
}

group = "com.kraktun"
version = "0.1.0"

val coroutinesVersion = "1.3.2"
val kotlinVersion = "1.3.50"

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
    maven(url = "https://jitpack.io")
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testCompile("junit:junit:4.12")
    compile("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    compile("com.github.ajalt:clikt:2.3.0")
    compile("com.github.Kraktun:KUtils:6f05036")
}

ktlint {
    verbose.set(true)
    outputToConsole.set(true)
    coloredOutput.set(true)
}

val fatJar = task("fatJar", type = Jar::class) {
    baseName = project.name
    manifest {
        attributes["Implementation-Title"] = "Servilio"
        attributes["Implementation-Version"] = version
        attributes["Main-Class"] = "com.kraktun.servilio.MainKt"
    }
    from(configurations.runtime.map { if (it.isDirectory) it else zipTree(it) })
    with(tasks["jar"] as CopySpec)
}

tasks {
    "build" {
        dependsOn(fatJar)
    }
}

tasks.withType<KotlinCompile>().all {
    kotlinOptions.freeCompilerArgs += "-Xuse-experimental=kotlin.Experimental"
    kotlinOptions.jvmTarget = "1.8"
}

