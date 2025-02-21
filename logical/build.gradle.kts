import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.parcelize)
    alias(libs.plugins.serialization)
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                }
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "logical"
            isStatic = true
            export(libs.decompose)
            export(libs.essenty.lifecycle)
            export(libs.essenty.stateKeeper) // Optional, only if you need state preservation on Darwin (Apple) targets
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.datetime)
                api(libs.decompose)
                api(libs.kotlinx.coroutines.core)
                api(libs.reaktive.reaktive)
                api(libs.kotlinx.kotlinxSerializationJson)
                api(libs.kotlinx.kotlinxSerializationCore)
                api(libs.essenty.lifecycle)
                api(libs.essenty.stateKeeper)

                // Ktor dependencies
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.ktor.client.logging)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        val iosMain by creating {
            dependsOn(commonMain)
            dependencies {
                api(libs.essenty.instanceKeeper)
                implementation(libs.ktor.client.darwin)
            }
        }

        iosX64Main {
            dependsOn(iosMain)
        }
        iosArm64Main {
            dependsOn(iosMain)
        }
        iosSimulatorArm64Main {
            dependsOn(iosMain)
        }
    }
}

android {
    namespace = "com.sega.videocaption"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}