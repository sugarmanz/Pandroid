plugins {
    kotlin("multiplatform")
    application
}

kotlin {
    jvm {
        withJava()
    }

    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(project(":pandora"))
                implementation(kotlinx("coroutines-core", Versions.coroutines))
            }
        }
    }
}

application {
    mainClass.set("com.jeremiahzucker.pandroid.MainKt")
}

tasks {
    named("run") {
        dependsOn(rootProject.tasks.getByName("clean"))
    }
}
