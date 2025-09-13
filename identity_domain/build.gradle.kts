plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias (libs.plugins.androidLibrary
    )
}

kotlin {
    androidTarget()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {

        }
    }
}
    android {
        namespace = "net.thechance.mena.identity.domain"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        defaultConfig {
            minSdk =  libs.versions.android.minSdk.get().toInt()
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
    }
