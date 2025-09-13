plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
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