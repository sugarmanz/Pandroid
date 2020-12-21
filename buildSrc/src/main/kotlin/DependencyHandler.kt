import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.arrow(module: String, version: String? = Versions.arrow) =
    frame("io.arrow-kt:arrow-", module, version)

fun DependencyHandler.sqlDelight(module: String, version: String? = Versions.sqlDelight) =
    frame("com.squareup.sqldelight:", module, version)

fun DependencyHandler.ktorClient(module: String, version: String? = Versions.ktor) =
    frame("io.ktor:ktor-client-", module, version)

fun DependencyHandler.kotlinx(module: String, version: String? = Versions.kotlin) =
    frame("org.jetbrains.kotlinx:kotlinx-", module, version)

fun DependencyHandler.frame(prefix: String, module: String, version: String? = null) =
    "$prefix$module${version?.let { ":$version" } ?: ""}"
