import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.kotlinx.rpc.platform)
}

kotlin {
    js(IR) {
        browser {
            commonWebpackConfig {
                outputFileName = "todo.js"
                cssSupport {
                    enabled.set(true)
                }
            }
        }
        binaries.executable()
    }

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
        freeCompilerArgs.add(
            "-Xcontext-receivers",
        )
    }

    sourceSets {
        jsMain.dependencies {
            implementation(projects.app.common)

            implementation(libs.kotlin.stdlib.js)
            implementation(libs.ktor.client.js)
            implementation(libs.ktor.client.websockets.js)
            implementation(libs.kotlinx.rpc.krpc.ktor.client)
            implementation(libs.kotlinx.rpc.krpc.serialization.json)

            implementation(libs.kotlinx.coroutines.core.js)
            implementation(libs.kotlin.extensions)
            implementation(libs.kotlinx.serialization.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(compose.runtime)
            implementation(compose.html.core)
            implementation(libs.router)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.js)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
        }

        jsTest.dependencies {
            implementation(kotlin("test-js"))
        }
    }
}

