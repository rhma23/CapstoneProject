plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.dicoding.projectcapstone"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dicoding.projectcapstone"
        minSdk = 24
        targetSdk = 34
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
}

dependencies {
    // Core libraries
    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.androidx.activity)

    // Testing libraries
    testImplementation(libs.test.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso)

    // Retrofit & Gson
    implementation(libs.retrofit)
    implementation(libs.gson.converter)
    implementation(libs.gson)

    // Lifecycle & Coroutines
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.coroutines.android)

}


