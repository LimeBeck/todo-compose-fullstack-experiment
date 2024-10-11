import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinx.serialization)
    application
}

repositories {
    mavenCentral()
}

java {
    dependencies {
            // This dependency is used by the application.
    }
}

// val jvmProcessResources = tasks.named<Copy>("jvmProcessResources")

// val jsCopyTask = tasks.create<Copy>("jsCopyTask") {
//     val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
//     from(jsBrowserDistribution)
//     into(jvmProcessResources.get().destinationDir.resolve("static"))
//     excludes.add("*.zip")
//     excludes.add("*.tar")
// }

// tasks.named<JavaExec>("run") {
//     dependsOn(tasks.named<Jar>("jvmJar"))
//     classpath(tasks.named<Jar>("jvmJar"))
// }

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
