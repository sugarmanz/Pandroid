plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")

    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("realm-android")
}

android {
    compileSdkVersion(29)
    defaultConfig {
        applicationId = "com.jeremiahzucker.pandroid"
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 2
        versionName = "1.0.1"
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    // Local
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.Kotlin}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")

    // Android support
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.1.0")
    implementation("com.google.android.material:material:1.2.1")
    implementation("androidx.constraintlayout:constraintlayout:2.0.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.0")

    // Analytics
    implementation("com.google.firebase:firebase-analytics-ktx:17.5.0")
    implementation("com.google.firebase:firebase-crashlytics-ktx:17.2.1")


    implementation("androidx.preference:preference:1.1.1")
    implementation("androidx.datastore:datastore-preferences:1.0.0-alpha01")

    // Networking
    implementation("com.squareup.retrofit2:retrofit:2.3.0")
    implementation("com.squareup.retrofit2:converter-gson:2.3.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.3.0")
    implementation("com.squareup.okhttp3:okhttp:3.12.8")
    implementation("com.squareup.okhttp3:logging-interceptor:3.9.0")
    implementation("com.squareup.picasso:picasso:2.5.2")

    // State persistence
    implementation("frankiesardo:icepick:3.2.0")
    kapt("frankiesardo:icepick-processor:3.2.0")

    // Reactive extensions
    implementation("io.reactivex.rxjava2:rxandroid:2.0.1")
    implementation("io.reactivex.rxjava2:rxjava:2.1.6")

    // Testing
    testImplementation("junit:junit:4.12")
    testImplementation("org.mockito:mockito-core:2.20.1")

    // UI Testing
    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.0")
}

tasks.check {
    dependsOn("addKtlintFormatGitPreCommitHook")
}
