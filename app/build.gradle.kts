plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.somanath.stackoverflowsearch"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.somanath.stackoverflowsearch"
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    implementation (libs.androidx.constraintlayout)
    implementation (libs.androidx.recyclerview)


    implementation (libs.material.v130)

//    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.3")

    //life Cycle
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.livedata.ktx)

    //Hilt for Dependency injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    //Retrofit for Api integration
    implementation (libs.retrofit2.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.okhttp)
    
    //RxJava

    implementation (libs.rxjava)
    implementation (libs.rxandroid)
    implementation (libs.adapter.rxjava3)

    // RxBinding
    implementation (libs.rxbinding)
    implementation (libs.rxbinding.core)
    implementation (libs.rxbinding.appcompat)



}