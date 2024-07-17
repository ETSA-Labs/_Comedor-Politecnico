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
        versionName = "1.0"

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
        dataBinding = true
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
    implementation (libs.androidx.appcompat)
    implementation (libs.androidx.constraintlayout)
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
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    /* QR scanner */

    /* ... other dependencies for Reservations...
     implementation "com.google.code.gson:gson:2.8.9"

    // Dependencias necesarias para manejar ActivityResultLauncher
     */

    //Dependencias OneSignal Notificaciones
    implementation(libs.onesignal)
    implementation ("com.onesignal:OneSignal:[4.4.1, 5.99.99]")
    implementation ("com.onesignal:OneSignal:4.4.1")
    implementation ("com.google.firebase:firebase-messaging:23.0.0")
    implementation ("com.google.firebase:firebase-messaging:23.0.0")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    //Integracion middleware AWS
    implementation(libs.retrofit2)
    implementation(libs.retrofit2convertergson) // Para convertir JSON a objetos Java/Kotlin
    implementation(libs.okhttp3)
    //Integracion Paypal
    implementation("com.paypal.sdk:paypal-android-sdk:2.16.0")




}