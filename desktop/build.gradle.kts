import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version "0.2.0-build132"
}

kotlin {
    jvm {
        withJava()
    }

    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(project(":pandora"))
                implementation(kotlinx("coroutines-core", Versions.coroutines))
                implementation("uk.co.caprica:vlcj:4.7.0")
            }
        }
    }
}

// application {
//     mainClass.set("com.jeremiahzucker.pandroid.MainKt")
// }

compose.desktop {
    application {
        // mainClass.set("com.jeremiahzucker.pandroid.MainKt")
        mainClass = "com.jeremiahzucker.pandroid.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "ImageViewer"
            modules("jdk.crypto.ec")

            val iconsRoot = project.file("../common/src/desktopMain/resources/images")
            macOS {
                iconFile.set(iconsRoot.resolve("icon-mac.icns"))
            }
            windows {
                iconFile.set(iconsRoot.resolve("icon-windows.ico"))
                menuGroup = "Compose Examples"
                // see https://wixtoolset.org/documentation/manual/v3/howtos/general/generate_guids.html
                upgradeUuid = "18159995-d967-4CD2-8885-77BFA97CFA9F"
            }
            linux {
                iconFile.set(iconsRoot.resolve("icon-linux.png"))
            }
        }
    }
}

// tasks {
//     named("run") {
//         dependsOn(rootProject.tasks.getByName("clean"))
//     }
// }
