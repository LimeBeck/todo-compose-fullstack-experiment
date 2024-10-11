import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kotlinx.rpc.platform)
    application
    distribution
}

dependencies {
    implementation(projects.app.common)
    implementation(libs.ktor.server.cio)
    implementation(libs.ktor.server.core.jvm)
    implementation(libs.ktor.server.cors.jvm)
    implementation(libs.ktor.server.websockets.jvm)

    implementation(libs.kotlinx.coroutines.core.jvm)
    implementation(libs.logback.classic)

    implementation(libs.kotlinx.rpc.krpc.ktor.server)
    implementation(libs.kotlinx.rpc.krpc.serialization.json)

    testImplementation(libs.kotlinx.rpc.krpc.client)
    testImplementation(libs.kotlinx.rpc.krpc.ktor.client)
    testImplementation(kotlin("test"))
    testImplementation(libs.ktor.server.test.host)
}

val jvmProcessResources = tasks.named<Copy>("processResources")

// val jsCopyTask = tasks.create<Copy>("jsCopyTask") {
//     val frontendDist = project(":app:frontend").tasks.named("jsBrowserProductionWebpack")
//     dependsOn(frontendDist)
//     from(frontendDist)
//     into(jvmProcessResources.get().destinationDir.resolve("static"))
//     excludes.add("*.zip")
//     excludes.add("*.tar")
// }

val buildAndCopyFrontend = tasks.register<Copy>("buildAndCopyFrontend") {
    val frontendDist = project(":frontend").tasks.named("jsBrowserProductionWebpack")
    dependsOn(frontendDist)
    from(frontendDist)
    into(jvmProcessResources.get().destinationDir.resolve("static"))
}

// val prepareAppResources = tasks.register("prepareAppResources") {
//     dependsOn(buildAndCopyFrontend)
//     finalizedBy("processResources")
// }

// val buildApp = tasks.register("buildApp") {
//     dependsOn(prepareAppResources)
//     finalizedBy("assemble")
// }

// tasks.create("runApp") {
//     group = "application"
//     dependsOn(buildApp)
//     finalizedBy(tasks.named("run"))
// }

tasks.named("jvmJar") {
    dependsOn(buildAndCopyFrontend)
}

tasks.named("jvmTest") {
    dependsOn(buildAndCopyFrontend)
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("jvmJar"))
    classpath(tasks.named<Jar>("jvmJar"))
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use Kotlin Test test framework
            useKotlinTest("2.0.0")
        }
    }
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    // Define the main class for the application.
    mainClass = "dev.limebeck.todo.ApplicationKt"
}
