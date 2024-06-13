plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    signingConfigs {
        getByName("debug") {
            storeFile =
                file("C:\\Users\\Ricardo\\Documents\\Android Studio\\Projects\\Comedor-Politecnico\\app\\src\\main\\assets\\app.keystore")
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

        manifestPlaceholders["auth0Domain"] = "@string/domain.auth0"
        manifestPlaceholders["auth0Scheme"] = "demo"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            buildConfigField("boolean", "DEBUG", "true")
        }
        release {
            buildConfigField("boolean", "DEBUG", "false")
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

    implementation(libs.androidx.core)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.biometric)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.com.squareup.retrofit2.retrofit2)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.gson)
    implementation(libs.msal)
}