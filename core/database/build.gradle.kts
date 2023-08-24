@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.sqldelight)
}

android {
    namespace = "garcia.ludovic.photos.core.database"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        targetSdk = 34

        testInstrumentationRunner = "garcia.ludovic.photos.core.testing.PhotosTestRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

ksp {
    // Room
    arg("room.schemaLocation", "$projectDir/src/main/room")
    arg("room.incremental", "true")
    arg("room.expandProjection", "true")
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
    implementation(libs.sqldelight.primitive.adapters)
    implementation(libs.sqldelight.runtime)

    ksp(libs.hilt.android.compiler)
    ksp(libs.room.compiler)

    testImplementation(project(":core:database-test"))
    testImplementation(project(":core:testing"))
    testImplementation(libs.sqldelight.sqlite.driver)

    androidTestImplementation(project(":core:database-test"))
    androidTestImplementation(project(":core:testing"))

    kspAndroidTest(libs.hilt.android.compiler)
    kspAndroidTest(libs.room.compiler)
}

sqldelight {
    databases {
        create("PhotosDatabase") {
            packageName.set("garcia.ludovic.photos.core.database")
            srcDirs.setFrom("src/main/sqldelight")
            schemaOutputDirectory.set(file("build/dbs"))
        }
    }
}
