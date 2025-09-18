import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
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

            //Koin
            implementation(libs.koin.core)
            api(libs.koin.annotations)
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