/*
 * This file was generated by the Gradle 'init' task.
 *
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
    `java-library`
    `maven-publish`
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    api(libs.org.springframework.boot.spring.boot.starter.web)
    // https://mvnrepository.com/artifact/jakarta.servlet/jakarta.servlet-api
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")
    api(libs.org.springframework.boot.spring.boot.starter.jdbc)
    api(libs.net.sourceforge.nekohtml.nekohtml)
    api(libs.org.springframework.boot.spring.boot.starter.thymeleaf)
    api(libs.org.xerial.sqlite.jdbc)
    runtimeOnly(libs.org.springframework.boot.spring.boot.devtools)
    testImplementation(libs.org.springframework.boot.spring.boot.starter.test)
}

group = "com.zerofancy"
version = "3.0.1-SNAPSHOT"
description = "theoremhelper"
java.sourceCompatibility = JavaVersion.VERSION_17

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}