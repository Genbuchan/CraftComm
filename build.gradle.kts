plugins {
    id("xyz.jpenilla.run-paper") version "2.1.0"
    kotlin("jvm") version "1.8.21"
}

group = "studio.genbu.mcplugins"
version = "0.1.0"

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("io.papermc.paper:paper-api:1.20-R0.1-SNAPSHOT")
    // compileOnly("dev.folia:folia-api:1.20-R0.1-SNAPSHOT")
    testImplementation(kotlin("test"))
}

tasks{

    jar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        configurations["runtimeClasspath"].forEach { file: File ->
            from(zipTree(file.absoluteFile))
        }
    }

    test {
        useJUnitPlatform()
    }

    runServer {
        dependsOn("jar")
        minecraftVersion("1.20")
    }

}

runPaper.folia.registerTask()

kotlin {
    jvmToolchain(17)
}
