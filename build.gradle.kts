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
//    implementation("org.jdbi:jdbi3-kotlin:3.12.2")
//    implementation("org.jdbi:jdbi3-postgres:3.12.2")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}