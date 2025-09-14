import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "FaithPresentation"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }

        commonMain.dependencies {
            implementation(projects.faithDomain)
            implementation(projects.designSystem)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
        }

        iosMain.dependencies {
            // iOS-specific main dependencies if needed
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlin.testJunit)
            implementation(libs.mockk.common)
            implementation(libs.truth)
            implementation(libs.turbine)
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
            implementation("io.kotest:kotest-assertions-core:5.8.0")
            implementation("io.kotest:kotest-framework-datatest:5.8.0")
        }

        androidUnitTest.dependencies {
            implementation(libs.junit)
            implementation(libs.mockk)
            implementation(libs.mockk.android)
            implementation("org.robolectric:robolectric:4.10.3")
            implementation("io.kotest:kotest-runner-junit4:5.8.0")
        }

        androidInstrumentedTest.dependencies {
            implementation(libs.androidx.ui.test.junit4)
            implementation(libs.androidx.testExt.junit)
            implementation(libs.androidx.espresso.core)
        }

        jvmTest.dependencies {
            implementation(libs.junit)
            implementation(libs.mockk)
            implementation(libs.mockk.agent.jvm)
            implementation("io.kotest:kotest-runner-junit5:5.8.0")
        }

        iosTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "net.thechance.mena.faith.presentation"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}