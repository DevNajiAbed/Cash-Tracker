import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.naji.cashtracker"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.naji.cashtracker"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.bundles.core.ktx)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.activity.compose)
    implementation(libs.bundles.compose.core)
    implementation(libs.bundles.compose.icons)
    implementation(libs.bundles.compose.tooling.preview)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.room)
    implementation(libs.bundles.navigation)
    implementation(libs.bundles.serialization)
    implementation(libs.bundles.datastore)
    implementation(libs.bundles.vico)
    implementation(libs.bundles.coil)

    debugImplementation(libs.bundles.compose.tooling.debug)
    debugImplementation(libs.bundles.compose.test.manifest.debug)

    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.android)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.bundles.compose.test)

    ksp(libs.bundles.room.compiler)
}
