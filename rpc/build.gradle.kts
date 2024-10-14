plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kotlinx.rpc.platform)
}

kotlin {
    jvm()

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

    sourceSets {
        jsMain.dependencies {
            implementation(libs.kotlin.stdlib.js)
            implementation(libs.ktor.client.js)
            implementation(libs.ktor.client.websockets.js)
            implementation(libs.kotlinx.rpc.krpc.core)
            implementation(libs.kotlinx.rpc.krpc.client)
            implementation(libs.kotlinx.rpc.krpc.serialization.json)

            implementation(libs.kotlinx.coroutines.core.js)
            implementation(libs.kotlin.extensions)
            implementation(libs.kotlinx.serialization.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.js)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
        }

        jvmMain.dependencies {
            implementation(libs.ktor.server.core.jvm)
            implementation(libs.ktor.server.websockets.jvm)
            implementation(libs.kotlinx.rpc.krpc.core)
            implementation(libs.kotlinx.rpc.krpc.server)
            implementation(libs.kotlinx.rpc.krpc.serialization.json)
        }
    }
}

dependencies {
    commonMainApi(libs.kotlin.stdlib)
    commonMainApi(libs.kotlinx.serialization.json)
    commonMainApi(libs.ktor.client.core)
    commonMainApi(libs.kotlinx.coroutines.core)
    commonMainApi(libs.kotlinx.rpc.core)
    commonMainApi(libs.kotlinx.rpc.krpc.core)
}