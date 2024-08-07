plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlinKapt)
}

android {
    namespace = "com.exam.myapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.exam.myapp"
        minSdk = 26
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

    dataBinding {
        enable = true
    }

    kapt {
        arguments {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.core.testing)
    testImplementation(libs.rxjava)
    testImplementation(libs.rxandroid)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.recyclerview)
    implementation(libs.databinding)
    implementation(libs.glide)
    kapt(libs.glide.compiler)
    implementation(libs.palette)

    implementation(libs.retrofit)
    implementation(libs.retrofitadapter)
    implementation(libs.converter.gson)
    implementation(libs.interceptor)
    implementation(libs.rxjava)
    implementation(libs.rxandroid)
    implementation(libs.room.runtime)
    implementation(libs.room.rxjava)
    annotationProcessor(libs.room.compiler)
    kapt(libs.room.compiler)
    implementation(libs.swiperefreshlayout)

    implementation(libs.dagger.runtime)
    implementation(libs.dagger.android)
    implementation(libs.dagger.androidsupport)
    kapt(libs.dagger.androidprocessor)
    kapt(libs.dagger.compiler)

    implementation(libs.lifecycle.extensions)
    implementation(libs.lifecycle.compiler)

}