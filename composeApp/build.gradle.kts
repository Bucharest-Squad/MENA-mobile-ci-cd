import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.io.ByteArrayOutputStream

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "net.thechance.mena"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "net.thechance.mena"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        buildConfig = true
    }

    flavorDimensions += "environment"

    productFlavors {
        create("development") {
            dimension = "environment"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
        }
        create("staging") {
            dimension = "environment"
            applicationIdSuffix = ".staging"
            versionNameSuffix = "-staging"
        }
        create("production") {
            dimension = "environment"
        }
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

abstract class GenerateBadgedIconTask : DefaultTask() {
    @get:Inject
    abstract val execOperations: ExecOperations

    @get:InputDirectory
    abstract val baseIconResDir: DirectoryProperty

    @get:InputFile
    abstract val bannerFile: RegularFileProperty

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @TaskAction
    fun generate() {
        val baseDir = baseIconResDir.get().asFile
        baseDir
            .walk()
            .filter {
                it.isFile &&
                        it.parentFile.name.startsWith("mipmap-") &&
                        it.name.startsWith("ic_launcher") &&
                        it.name.endsWith(".png")
            }
            .forEach { iconFile ->
                val densityDirName = iconFile.parentFile.name
                val widthBaos = ByteArrayOutputStream()
                execOperations.exec {
                    commandLine("magick", "identify", "-format", "%w", iconFile.path)
                    standardOutput = widthBaos
                }
                val iconWidth = widthBaos.toString().trim()
                val outDir = outputDir.get().asFile
                val mipmapDir = outDir.resolve(densityDirName)
                mipmapDir.mkdirs()
                val resizedBanner = temporaryDir.resolve("${densityDirName}_banner_resized.png")
                val outputIconPath = mipmapDir.resolve(iconFile.name).path
                execOperations.exec {
                    commandLine("magick", bannerFile.get().asFile.path, "-resize", "${iconWidth}x${iconWidth}", resizedBanner.path)
                }
                execOperations.exec {
                    commandLine("magick", "composite", "-gravity", "southeast", resizedBanner.path, iconFile.path, outputIconPath)
                }
            }
    }
}

androidComponents {
    onVariants { variant ->
        if (variant.flavorName == "development" || variant.flavorName == "staging") {
            val bannerFileName = if (variant.flavorName == "development") "banner_dev.png" else "banner_staging.png"
            val task = tasks.register<GenerateBadgedIconTask>("generate${variant.name.replaceFirstChar { it.uppercase() }}BadgedIcon") {
                group = "Icon Generation"
                description = "Generates a badged icon for the ${variant.name} build."
                baseIconResDir.set(project.layout.projectDirectory.dir("src/androidMain/res"))
                bannerFile.set(project.file("build-assets/$bannerFileName"))
                outputDir.set(layout.buildDirectory.dir("generated/icons/res"))
            }
            variant.sources.res?.addGeneratedSourceDirectory(
                taskProvider = task,
                wiredWith = GenerateBadgedIconTask::outputDir
            )
        }
    }
}

