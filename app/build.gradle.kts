plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.espoch.comedor"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.espoch.comedor"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    /* basic */
    implementation(libs.androidx.core)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.appcompat)
    /* navigation */
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    /* biometric */
    implementation(libs.androidx.biometric)
    /* async */
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    /* ... other dependencies for Reservations...
     implementation "com.google.code.gson:gson:2.8.9"

    // Dependencias necesarias para manejar ActivityResultLauncher
     */


    implementation(project(":admin"))
    implementation(project(":customer"))
    implementation(project(":shared"))
    implementation(kotlin("script-runtime"))
}