plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.dreamsoftware.coinscope"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.dreamsoftware.coinscope"
        minSdk = 26
        targetSdk = 35
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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // --------------------------------------------------------
    // Core Libraries
    // --------------------------------------------------------

    // Desugar JDK libraries (required for certain Java 8+ features)
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    // --------------------------------------------------------
    // Project Modules
    // --------------------------------------------------------

    // Domain module for business logic and domain models
    implementation(project(":domain"))

    // Data module for data layer
    implementation(project(":data"))

    // UI module for Android UI components
    implementation(project(":ui"))

    // Utilities module for helper functions and extensions
    implementation(project(":utils"))

    // --------------------------------------------------------
    // Dependency Injection (DI) - Hilt
    // --------------------------------------------------------

    // Hilt for Dependency Injection (DI)
    implementation(libs.hilt.android)

    // Hilt compiler for annotation processing
    ksp(libs.hilt.compiler)

    // --------------------------------------------------------
    // Networking (Ktor)
    // --------------------------------------------------------

    // Ktor client libraries for HTTP requests, networking, and handling APIs
    implementation(libs.bundles.ktor)
}