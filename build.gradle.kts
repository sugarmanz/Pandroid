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
        val kotlin by Versions
        val sqlDelight by Versions
        classpath("com.android.tools.build:gradle:4.1.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin")
        classpath("io.realm:realm-gradle-plugin:4.3.4")
        classpath("com.google.gms:google-services:4.3.3")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.3.0")
        classpath("org.jmailen.gradle:kotlinter-gradle:3.0.2")
        classpath("com.squareup.sqldelight:gradle-plugin:$sqlDelight")
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
        maven("https://dl.bintray.com/arrow-kt/arrow-kt/")
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
    subprojects.forEach { delete(it.buildDir) }
}
