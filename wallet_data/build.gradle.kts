import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        androidMain.dependencies {
            //ktor
            implementation(libs.ktor.client.android)
            implementation(libs.ktor.client.okhttp)

        }
        commonMain.dependencies {
            implementation(projects.walletDomain)

            //ktor
            implementation(libs.bundles.ktor)
        }
        iosMain.dependencies {

            //ktor
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "net.thechance.mena.wallet.data"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}