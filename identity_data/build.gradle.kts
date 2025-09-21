import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kover)
}

kotlin {
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
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
            implementation(libs.ktor.client.cio)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmTest.dependencies {
            implementation(libs.bundles.jvm.test)
        }
    }
}

kover.reports {
    verify {
        rule {
            minBound(80)
        }
    }
}

val generateBuildConfig by tasks.registering {
    val outputDir = layout.buildDirectory.dir("generated/buildConfig")

    val props = Properties().apply {
        load(rootProject.file("local.properties").inputStream())
    }

    val baseUrl = props["BASE_URL"]
        ?: throw IllegalStateException("BASE_URL not found in local.properties")

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
