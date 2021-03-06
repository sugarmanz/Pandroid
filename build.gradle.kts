buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
        mavenLocal()
        maven("https://maven.google.com")
        maven("http://oss.jfrog.org/artifactory/oss-snapshot-local")
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Kotlin}")
        classpath("io.realm:realm-gradle-plugin:4.3.4")
        classpath("com.google.gms:google-services:4.3.3")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.3.0")
        classpath("org.jmailen.gradle:kotlinter-gradle:3.0.2")
    }
}

plugins {
    id("net.researchgate.release") version "2.6.0"
}

allprojects {
    apply(plugin = "org.jmailen.kotlinter")

    repositories {
        google()
        jcenter()
        mavenCentral()
        mavenLocal()
        maven("https://maven.google.com")
        maven("http://oss.jfrog.org/artifactory/oss-snapshot-local")
        maven("https://jitpack.io")
        maven("https://clojars.org/repo/")
    }
}

val build by tasks.creating

release {
    failOnCommitNeeded = false
    failOnPublishNeeded = false
    failOnUnversionedFiles = false
    failOnUpdateNeeded = false
}


val getVersion by tasks.creating {
    doLast {
        println(version)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
