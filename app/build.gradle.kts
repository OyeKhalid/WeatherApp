plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.weatherapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.weatherapp"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures{
        viewBinding = true
    }
}

dependencies {
//    kapt(libs.hilt.compiler) // ✅ This must be present

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.cardview)

    implementation(libs.retrofit2.retrofit)
    implementation(libs.retrofit2.converter.gson)

    implementation(libs.jetbrains.kotlinx.coroutines.android)
    implementation(libs.jetbrains.kotlinx.coroutines.core)

    implementation(libs.squareup.picasso)

    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
//    implementation(libs.javapoet)

    // Dagger Hilt
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}