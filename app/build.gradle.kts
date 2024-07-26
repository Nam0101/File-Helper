plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    //ksp
    id("com.google.devtools.ksp") version "2.0.0-1.0.23"

}

android {
    namespace = "nv.nam.filehelper"
    compileSdk = 34

    defaultConfig {
        applicationId = "nv.nam.filehelper"
        minSdk = 29
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(project(":app:documentlibary"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // coroutine
//    implementation(libs.kotlinx.coroutines.android)
//    //flow
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//
//    // google auth
//    implementation(libs.play.services.auth)
//    //room
//    implementation(libs.androidx.room.runtime)
//    ksp(libs.androidx.room.compiler)

}