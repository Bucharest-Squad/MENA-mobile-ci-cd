plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        androidMain.dependencies {

        }
        commonMain.dependencies {
            implementation(projects.coreChatDomain)
            implementation(libs.kotlin.serialization)
            implementation(libs.contacts.provider)
        }
        iosMain.dependencies {

        }
    }
}