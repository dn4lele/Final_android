plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.final_android"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.final_android"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    //glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    //https
    val retrofitVersion="2.11.0"
    val gsonConverterVersion="2.11.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$gsonConverterVersion")
    //database
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    //chip-navigation
    implementation("com.github.ismaeldivita:chip-navigation-bar:1.4.0")

    //blur-bg
    implementation("com.github.Dimezis:BlurView:version-2.0.3")

    //viewpager2
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)


}