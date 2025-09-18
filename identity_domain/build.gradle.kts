plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    jvm()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {

        }

        jvmTest.dependencies {
            implementation(libs.bundles.jvm.test)
        }
    }
}