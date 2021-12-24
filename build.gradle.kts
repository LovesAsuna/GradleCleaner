plugins {
    kotlin("jvm") version "1.6.10"
    id("java")
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "com.hyosakura"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// 日志
dependencies {
    implementation("org.apache.logging.log4j:log4j-api:2.16.0")
    implementation("org.apache.logging.log4j:log4j-core:2.17.0")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.16.0")
    implementation("org.slf4j:slf4j-api:1.7.32")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.13.1")
}

// 工具
dependencies {
    implementation("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
    testImplementation("org.junit.jupiter:junit-jupiter:5.830")
    implementation("info.picocli:picocli:4.6.2")
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "com.hyosakura.cleaner.Main"
        )
    }
}

tasks.test {
    useJUnitPlatform()
}