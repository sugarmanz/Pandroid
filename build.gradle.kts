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
        classpath("org.jlleitschuh.gradle:ktlint-gradle:9.4.0")
    }
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint-idea")

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

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
