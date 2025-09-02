plugins {
    alias(libs.plugins.application)
    alias(libs.plugins.parcelize)
    alias(libs.plugins.kapt)
    alias(libs.plugins.crashlytics)
    alias(libs.plugins.google.dagger.hilt)
    alias(libs.plugins.devtools)
    alias(libs.plugins.google.gms.service)

    kotlin("android")
}

android {
    namespace = "com.devseok.dailymanager"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.devseok.dailymanager"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kapt {
        correctErrorTypes = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }

    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.corx.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.androidx.constraintlayout)

    androidTestImplementation(libs.androidx.test.espresso)

    implementation(libs.compose.activity)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.ui.material3)
    implementation(libs.androidx.ui.livedata)
    implementation(libs.androidx.ui.lifecycle)

    implementation(libs.androidx.ui.foundation)
    implementation(libs.androidx.ui.navigation)
    implementation(libs.androidx.ui.hilt)

    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.junit4)

    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)

    implementation(libs.rxjava2)
    implementation(libs.rxjava2.rxandroid)
    implementation(libs.rxjava2.adapter)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)
    implementation(libs.retrofit.logging)
    implementation(libs.retrofit.simplexml)
    implementation(libs.retrofit.adapter)
    implementation(libs.retrofit.jaxb)
    implementation(libs.okhttp)

    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)

    implementation(libs.rxjava3.rxkotlin)
    implementation(libs.rxjava3.rxandroid)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.fragment)

    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.org.commons.lang3)

    implementation(platform(libs.firebase.bom))
    implementation(libs.messaing)
    implementation(libs.crashlytics)
    implementation(libs.analytics)
    implementation(libs.auth)
    implementation(libs.firestore)
    implementation(libs.play.services.auth)

    // hilt
    implementation(libs.google.hilt)
    kapt(libs.google.hilt.compiler)

    implementation(libs.androidx.runtime)
    implementation(libs.kotlin.stdlib)

        implementation(libs.compose.colorpicker)

}