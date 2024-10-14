plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kotlinx.rpc)
}

kotlin {
    jvm()
    js {
        browser()
        binaries.executable()
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