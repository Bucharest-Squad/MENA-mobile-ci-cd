import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidTarget()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.datastore.preferences)
            implementation(libs.androidx.security.crypto)
            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(projects.identityDomain)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.koin.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.auth)
            implementation(libs.multiplatform.settings)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }

}

android {
    namespace = "net.thechance.mena.identity.data"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
dependencies {
    implementation(projects.identityDomain)
}

val generateBuildConfig by tasks.registering {
    val outputDir = layout.buildDirectory.dir("generated/buildConfig")

    val props = Properties().apply {
        load(rootProject.file("local.properties").inputStream())
    }

    val baseUrl = props["BASE_URL"] ?: "https://test/"

    outputs.dir(outputDir)

    doLast {
        val file = outputDir.get().file("BuildConfig.kt").asFile
        file.parentFile.mkdirs()
        file.writeText(
            """
            object BuildConfig {
                const val BASE_URL: String = "$baseUrl"
            }
            """.trimIndent()
        )
    }
}

kotlin.sourceSets["commonMain"].kotlin.srcDir(
    layout.buildDirectory.dir("generated/buildConfig")
)

tasks.withType<KotlinCompile>().configureEach {
    dependsOn(generateBuildConfig)
}
