plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        androidMain.dependencies {

        }
        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(projects.dukanDomain)
            implementation(libs.bundles.ktor)
        }
        iosMain.dependencies {

        }
    }
}