plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kotlinx.rpc.platform)
    application
    distribution
}

dependencies {
    implementation(projects.app.common)
    implementation(projects.rpc)

    implementation(libs.ktor.server.cio)
    implementation(libs.ktor.server.core.jvm)
    implementation(libs.ktor.server.cors.jvm)
    implementation(libs.ktor.server.websockets.jvm)

    implementation(libs.kotlinx.coroutines.core.jvm)
    implementation(libs.logback.classic)
    implementation(libs.hoplite.core)
    implementation(libs.hoplite.yaml)
    implementation(libs.arrow.suspendapp)

    implementation(libs.kotlinx.rpc.krpc.serialization.json)

    testImplementation(libs.kotlinx.rpc.krpc.client)
    testImplementation(kotlin("test"))
    testImplementation(libs.ktor.server.test.host)
}

val jvmProcessResources = tasks.named<Copy>("processResources")

val buildAndCopyFrontend = tasks.register<Copy>("buildAndCopyFrontend") {
    val frontendDist = project(":app:frontend").tasks.named("jsBrowserDistribution")
    dependsOn(frontendDist)
    from(frontendDist)
    into(jvmProcessResources.get().destinationDir.resolve("static"))
    excludes.add("*.zip")
    excludes.add("*.tar")
    excludes.add("*.map")
    excludes.add("webpack.config.js")
}

tasks.named("jar") {
    dependsOn(buildAndCopyFrontend)
}

tasks.named("test") {
    dependsOn(buildAndCopyFrontend)
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("jar"))
    classpath(tasks.named<Jar>("jar"))
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
