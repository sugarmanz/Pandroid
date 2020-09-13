buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
        mavenLocal()
        maven(url = "https://maven.google.com")
        maven(url = "http://oss.jfrog.org/artifactory/oss-snapshot-local")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Kotlin}")
        classpath("io.realm:realm-gradle-plugin:4.3.4")
        classpath("com.google.gms:google-services:4.3.3")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.3.0")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        mavenLocal()
        maven(url = "https://maven.google.com")
        maven(url = "http://oss.jfrog.org/artifactory/oss-snapshot-local")
        maven(url = "https://jitpack.io")
        maven(url = "https://clojars.org/repo/")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
