plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    signingConfigs {
        getByName("debug") {
            storeFile =
                file("src\\main\\assets\\app.keystore")
            storePassword = "@@proyectolmm@@"
            keyPassword = "@@proyectolmm@@"
            keyAlias = "comedor"
        }
    }
    namespace = "com.espoch.comedor"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.espoch.comedor"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {

        }

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
        buildConfig = true
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
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    /* biometric */
    implementation(libs.androidx.biometric)
    /* async */
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    /* firebase */
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore)
    /* MSAL */
    implementation(libs.com.squareup.retrofit2.retrofit2)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.gson)
    implementation(libs.msal)
    /* QR generator */
    implementation(libs.alex.qr.generator)
    /* QR scanner */
    /* MAP */
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
    implementation(libs.maps.utils)
    implementation(libs.okhttp)

    /* ... other dependencies for Reservations...
     implementation "com.google.code.gson:gson:2.8.9"

    // Dependencias necesarias para manejar ActivityResultLauncher
     */

}