plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version Versions.Kotlin
    `java-library`

    id("com.squareup.sqldelight")
}

val ktorVersion = "1.4.3"
val serializationVersion = "1.0.1"
val coroutinesVersion = "1.4.2"
val sqlDelightVersion = "1.4.3"
val arrowVersion = "0.10.4"

fun DependencyHandler.arrow(module: String, version: String? = null) =
    frame("io.arrow-kt:arrow-", module, version)

fun DependencyHandler.sqlDelight(module: String, version: String? = null) =
    frame("com.squareup.sqldelight:", module, version)

fun DependencyHandler.ktorClient(module: String, version: String? = null) =
    frame("io.ktor:ktor-client-", module, version)

fun DependencyHandler.kotlinx(module: String, version: String? = null) =
    frame("org.jetbrains.kotlinx:kotlinx-", module, version)

fun DependencyHandler.frame(prefix: String, module: String, version: String? = null) =
    "$prefix$module${version?.let { ":$version" } ?: ""}"


dependencies {
    implementation(kotlin("reflect"))

    implementation(kotlinx("coroutines-core", coroutinesVersion))
    implementation(kotlinx("serialization-json", serializationVersion))

    implementation(ktorClient("core", ktorVersion))
    implementation(ktorClient("serialization", ktorVersion))
    implementation(ktorClient("logging", ktorVersion))

    implementation(sqlDelight("runtime", sqlDelightVersion))

    implementation(arrow("core", arrowVersion))
    implementation(arrow("syntax", arrowVersion))

    // JVM deps
    implementation(ktorClient("okhttp", ktorVersion))
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation(sqlDelight("sqlite-driver", sqlDelightVersion))
}

sqldelight {
    database("AppDatabase") {
        packageName = "com.jeremiahzucker.pandroid.cache"
    }
}
