plugins {
    kotlin("jvm") version "1.9.23"
}

group = "com.github.ajcode404"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.postgresql:postgresql:42.7.4")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

tasks.register<JavaExec>("run") {
    group = "application"
    mainClass.set("com.github.ajcode404.MainKt")
    classpath = sourceSets["main"].runtimeClasspath
}