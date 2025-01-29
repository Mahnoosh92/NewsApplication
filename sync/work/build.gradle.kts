plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id ("com.google.dagger.hilt.android")
    id ("kotlin-kapt")
}

android {
    namespace = "com.mahnoosh.work"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    flavorDimensions += "version"
    productFlavors {
        create("demo") {
            dimension = "version"
        }
        create("prod") {
            dimension = "version"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(project(":core:data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.work.ktx)

    kapt(libs.hilt.ext.compiler)
    implementation("com.google.dagger:hilt-android:2.54")
    kapt("com.google.dagger:hilt-compiler:2.54")

    implementation(libs.androidx.work.ktx)
    implementation(libs.hilt.ext.work)
}