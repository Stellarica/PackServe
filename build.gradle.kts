plugins {
    kotlin("jvm")
    kotlin("kapt")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "net.stellarica"
version = "1.0-SNAPSHOT"

repositories {
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    implementation("com.sksamuel.hoplite:hoplite-core:${property("hoplite_version")}")
    implementation("com.sksamuel.hoplite:hoplite-hocon:${property("hoplite_version")}")

    implementation("org.http4k:http4k-bom:${property("http4k_version")}")
    implementation("org.http4k:http4k-core:${property("http4k_version")}")
    implementation("org.http4k:http4k-server-jetty:${property("http4k_version")}")

    compileOnly("com.velocitypowered:velocity-api:${property("velocity_version")}")
    kapt("com.velocitypowered:velocity-api:${property("velocity_version")}")
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}

kotlin {
    jvmToolchain(17)
}
