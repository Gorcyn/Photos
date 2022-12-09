@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.sqldelight)
}

android {
    namespace = "garcia.ludovic.photos.core.database"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33

        testInstrumentationRunner = "garcia.ludovic.photos.core.testing.PhotosTestRunner"

        // Room
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/src/main/room",
                    "room.incremental" to "true",
                    "room.expandProjection" to "true"
                )
            }
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
    implementation(project(":core:common"))

    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.hilt.android)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    implementation(libs.sqldelight.android.driver)
    implementation(libs.sqldelight.coroutines.extensions)
    implementation(libs.sqldelight.runtime)

    kapt(libs.hilt.android.compiler)
    kapt(libs.room.compiler)

    testImplementation(project(":core:database-test"))
    testImplementation(project(":core:testing"))
    testImplementation(libs.sqldelight.sqlite.driver)

    androidTestImplementation(project(":core:database-test"))
    androidTestImplementation(project(":core:testing"))

    kaptAndroidTest(libs.hilt.android.compiler)
    kaptAndroidTest(libs.room.compiler)
}

kapt {
    correctErrorTypes = true
}

sqldelight {
    database("PhotosDatabase") {
        packageName = "garcia.ludovic.photos.core.database"
        sourceFolders = listOf("sqldelight")
        schemaOutputDirectory = file("build/dbs")
    }
}
