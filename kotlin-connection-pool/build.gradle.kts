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
    implementation("com.h2database:h2:2.3.232")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}