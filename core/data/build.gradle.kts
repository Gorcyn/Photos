@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "garcia.ludovic.photos.core.data"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33

        testInstrumentationRunner = "garcia.ludovic.photos.core.testing.PhotosTestRunner"
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
    implementation(project(":core:common"))
    implementation(project(":core:database"))
    implementation(project(":core:network"))

    implementation(libs.hilt.android)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.play.services)

    kapt(libs.hilt.android.compiler)

    testImplementation(project(":core:testing"))
    testImplementation(project(":core:data-test"))
    testImplementation(project(":core:database-test"))
    testImplementation(project(":core:network-test"))

    androidTestImplementation(project(":core:testing"))
    androidTestImplementation(project(":core:data-test"))
    androidTestImplementation(project(":core:database-test"))
    androidTestImplementation(project(":core:network-test"))

    kaptAndroidTest(libs.hilt.android.compiler)
}

kapt {
    correctErrorTypes = true
}
