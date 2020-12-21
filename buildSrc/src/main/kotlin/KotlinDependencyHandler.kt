fun arrow(module: String, version: String? = null) =
    frame("io.arrow-kt:arrow-", module, version)

fun sqlDelight(module: String, version: String? = null) =
    frame("com.squareup.sqldelight:", module, version)

fun ktorClient(module: String, version: String? = null) =
    frame("io.ktor:ktor-client-", module, version)

fun kotlinx(module: String, version: String? = null) =
    frame("org.jetbrains.kotlinx:kotlinx-", module, version)

fun frame(prefix: String, module: String, version: String? = null) =
    "$prefix$module${version?.let { ":$version" } ?: ""}"
