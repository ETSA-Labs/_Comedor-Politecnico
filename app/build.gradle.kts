plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")
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
    /* navigation */
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    /* biometric */
    implementation(libs.androidx.biometric)
    /* async */
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    /* firebase */
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.database.ktx)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.analytics.ktx)
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
    implementation(libs.zxing)
    implementation(libs.zxing.android.embedded)
    implementation(kotlin("reflect"))
    /* MAP */
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
    implementation(libs.maps.utils)
    implementation(libs.okhttp)
    /* AWS */
    implementation(libs.aws.android.sdk.core)
    implementation(libs.aws.android.sdk.lambda)

    /* Brevo */
    implementation(libs.code.gson)
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
    implementation ("com.braintreepayments.api:drop-in:6.13.0")
    // to offer card payments
    implementation ("com.braintreepayments.api:card:4.39.0")

    // to collect device data
    implementation ("com.braintreepayments.api:data-collector:4.39.0")

    // to offer PayPal
    implementation ("com.braintreepayments.api:paypal:4.39.0")

    implementation ("com.stripe:stripe-android:20.5.0")
    implementation ("com.stripe:stripe-java:26.0.0")
    implementation ("com.github.kittinunf.fuel:fuel:2.3.1")

    implementation(libs.play.services.cast.tv)
}