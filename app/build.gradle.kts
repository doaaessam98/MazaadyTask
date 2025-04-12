plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    //alias(libs.plugins.kotlin.ksp)
    id ("kotlin-kapt")
    id("com.google.devtools.ksp")
    id ("com.google.dagger.hilt.android") version "2.51.1" apply false
    id ("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")

    id ("kotlin-parcelize")

}

android {
    namespace = "com.doaa.mazaadytask"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.doaa.mazaadytask"
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
    
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        dataBinding = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.runtime.android)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.paging.runtime.ktx)

    // hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    implementation(libs.androidx.runner)
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")

    kapt ("androidx.hilt:hilt-compiler:1.2.0")




    //room
    implementation ("androidx.room:room-paging:2.7.0")
    implementation ("androidx.room:room-runtime:2.7.0")
    kapt ("androidx.room:room-compiler:2.7.0")
    implementation ("androidx.room:room-ktx:2.7.0")

    //retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.logging.interceptor)

    //serialization
    implementation(libs.kotlinx.serialization.json)

    implementation (libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // coil
    implementation(libs.coil)

//lo

    implementation ("com.airbnb.android:lottie:3.4.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
            androidTestImplementation ("androidx.arch.core:core-testing:2.1.0")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation ("androidx.arch.core:core-testing:2.1.0")
    testImplementation ("androidx.room:room-testing:2.6.1")
    testImplementation ("androidx.arch.core:core-testing:2.2.0")


}
//kapt {
//    correctErrorTypes = true
//}