plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        androidMain.dependencies {

        }
        commonMain.dependencies {
            implementation(projects.coreChatDomain)
            implementation(libs.kotlin.serialization)
            implementation(libs.contacts.provider)
            implementation(libs.koin.core)
        }
        iosMain.dependencies {

        }
    }
}

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    namespace = "net.thechance.mena.core_chat_data"
}
