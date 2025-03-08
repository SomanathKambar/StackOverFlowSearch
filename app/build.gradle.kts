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
//        testInstrumentationRunner = "androidx.test.ext.junit.runners.AndroidJUnit4"
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

    packaging {
        resources.excludes.addAll(
            listOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
        )
        )
        jniLibs {
            useLegacyPackaging = true
        }
    }

    testOptions {
        unitTests.all {
//            useJUnitPlatform()
        }
    }
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)


    implementation (libs.androidx.constraintlayout)
    implementation (libs.androidx.recyclerview)


    implementation (libs.material.v130)

//    implementation ")org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.3")

    //life Cycle
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.livedata.ktx)

    //Hilt for Dependency injection
    implementation(libs.hilt.android)
    implementation(libs.androidx.runner)
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

    testImplementation (libs.junit)
    testImplementation ("org.mockito:mockito-core:5.14.2")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation ("io.reactivex.rxjava3:rxjava:3.1.10")
    testImplementation (libs.androidx.core.testing)

    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")


}