plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.dreamsoftware.coinscope.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://api.coincap.io/v2/\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField("String", "BASE_URL", "\"https://api.coincap.io/v2/\"")
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

    // Utilities module for helper functions and extensions
    implementation(project(":utils"))

    // --------------------------------------------------------
    // Ktor - Networking and HTTP Client
    // --------------------------------------------------------

    // Ktor client libraries for HTTP requests, networking, and handling APIs
    implementation(libs.bundles.ktor)

    // --------------------------------------------------------
    // Testing Libraries
    // --------------------------------------------------------

    // JUnit for unit testing
    testImplementation(libs.junit)
}
