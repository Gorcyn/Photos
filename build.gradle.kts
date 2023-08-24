@file:Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed

import java.net.URL
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.dokka) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.secrets) apply false
    alias(libs.plugins.sqldelight) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.sqldelight.gradlePlugin)
        classpath(libs.dokka.basePlugin)
    }
}

subprojects {
    apply(plugin = "org.jetbrains.dokka")

    // configure all format tasks at once
    tasks.withType<DokkaTask>().configureEach {
        val isSubmodule = arrayOf("core", "feature").contains(project.parent?.name)
        val isApp = project.parent?.name == "Photos" && project.name == "app"
        if (isSubmodule || isApp) {
            dokkaSourceSets.named("main") {
                val relPath = rootProject.projectDir.toPath().relativize(projectDir.toPath())
                outputDirectory.set(rootDir.resolve("docs/$relPath"))
                sourceLink {
                    val sources = "src/main/java/"
                    localDirectory.set(projectDir.resolve(sources))
                    remoteUrl.set(URL("https://github.com/gorcyn/photos/tree/feat/dokka/$relPath/$sources"))
                    remoteLineSuffix.set("#L")
                }

                displayName.set("Android")
            }
            pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
                customAssets = listOf(file(rootDir.resolve("app/logo-icon.svg")))
                footerMessage = "Copyright © ${java.time.Year.now().value} Ludovic Garcia"
            }
        }
    }
}

apply {
    from("gradle/projectDependencyGraph.gradle")
}
