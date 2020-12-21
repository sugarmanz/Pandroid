plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version Versions.kotlin
    id("com.android.library")

    id("com.squareup.sqldelight")
}

val coroutines by Versions
val serialization by Versions
val ktor by Versions
val sqlDelight by Versions
val arrow by Versions

kotlin {

    android()
    jvm("desktop")

    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(kotlin("reflect"))

                implementation(kotlinx("coroutines-core", coroutines))
                implementation(kotlinx("serialization-json", serialization))

                implementation(ktorClient("core", ktor))
                implementation(ktorClient("serialization", ktor))
                implementation(ktorClient("logging", ktor))

                implementation(sqlDelight("runtime", sqlDelight))

                implementation(arrow("core", arrow))
                implementation(arrow("syntax", arrow))
            }
        }

        named("androidMain") {
            dependencies {
                implementation(ktorClient("android", ktor))
                implementation(sqlDelight("android-driver", sqlDelight))
                implementation("ch.qos.logback:logback-classic:1.2.3")
            }
        }

        named("desktopMain") {
            dependencies {
                implementation(ktorClient("okhttp", ktor))
                implementation(sqlDelight("sqlite-driver", sqlDelight))
                implementation("ch.qos.logback:logback-classic:1.2.3")
            }
        }
    }

}

// android {
//     compileSdkVersion(29)
//     defaultConfig {
//         applicationId = "com.jeremiahzucker.pandroid"
//         minSdkVersion(21)
//         targetSdkVersion(29)
//         versionCode = version
//             .replace(".", "")
//             .replace("-SNAPSHOT", "")
//             .let(Integer::parseInt)
//         versionName = version
//         vectorDrawables.useSupportLibrary = true
//         testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//         multiDexEnabled = true
//     }
//     buildTypes {
//         getByName("release") {
//             isMinifyEnabled = false
//             proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
//         }
//     }
// }

android {
    compileSdkVersion(29)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

sqldelight {
    database("AppDatabase") {
        packageName = "com.jeremiahzucker.pandroid.cache"
    }
}
