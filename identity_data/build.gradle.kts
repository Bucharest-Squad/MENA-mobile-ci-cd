import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kover)
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
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

    filters.excludes {
        packages("*.di", "*.dto", "*.utils")
    }
}

val generateBuildConfig by tasks.register("generateEnvironmentXcconfig") {
    val developmentUrl = localProperties.getProperty("BASE_URL_DEVELOPMENT", "")
    val stagingUrl = localProperties.getProperty("BASE_URL_STAGING", "")
    val productionUrl = localProperties.getProperty("BASE_URL_PRODUCTION", "")
    val buildType = providers.environmentVariable("CONFIGURATION").orNull ?: ""

    val baseUrl = when {
        buildType.endsWith("Staging", ignoreCase = true) -> stagingUrl
        buildType.endsWith("Production", ignoreCase = true) -> productionUrl
        else -> developmentUrl
    }

    val outputFileProperty =
        project.layout.buildDirectory.file("generated/ios/environment.xcconfig")

    doLast {
        val outputFile = outputFileProperty.get().asFile
        outputFile.parentFile.mkdirs()
        outputFile.writeText("BASE_URL=$baseUrl")
        println("Generated environment.xcconfig")
    }
}

kotlin.sourceSets["commonMain"].kotlin.srcDir(
    layout.buildDirectory.dir("generated/buildConfig")
)

tasks.withType<KotlinCompile>().configureEach {
    dependsOn(generateBuildConfig)
}
