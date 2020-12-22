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
                implementation("uk.co.caprica:vlcj:4.7.0")
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
