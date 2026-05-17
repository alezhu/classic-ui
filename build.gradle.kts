import org.jetbrains.changelog.Changelog
import org.jetbrains.changelog.markdownToHTML

fun properties(key: String) = providers.gradleProperty(key)
fun environment(key: String) = providers.environmentVariable(key)

plugins {
    alias(libs.plugins.intelliJPlatform) // IntelliJ Platform Gradle Plugin
    alias(libs.plugins.changelog) // Gradle Changelog Plugin
    alias(libs.plugins.libVersionsUpdater)
}

group = properties("pluginGroup").get()
version = properties("pluginVersion").get()
val platformVersion = properties("platformVersion")
//val platformType = properties("platformType").get()

// Configure project's dependencies
repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

//// Dependencies are managed with Gradle version catalog - read more: https://docs.gradle.org/current/userguide/platforms.html#sub:version-catalog
dependencies {
    intellijPlatform {
        intellijIdea(platformVersion)
//
//        val parser = { it: String -> it.split(',').map(String::trim).filter(String::isNotEmpty) }
//        // Plugin Dependencies. Uses `platformBundledPlugins` property from the gradle.properties file for bundled IntelliJ Platform plugins.
////        bundledPlugins(properties("platformBundledPlugins").map(parser))
//
//        // Plugin Dependencies. Uses `platformPlugins` property from the gradle.properties file for plugin from JetBrains Marketplace.
//        plugins(properties("platformPlugins").map(parser))
//
////        instrumentationTools()
        pluginVerifier()
    }
}

//// Set the JVM language level used to build the project.
//kotlin {
//    jvmToolchain(17)
//}
//
//// Configure IntelliJ Platform Gradle Plugin - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-extension.html
intellijPlatform {
    projectName = project.name
    pluginConfiguration {
        // Extract the <!-- Plugin description --> section from README.md and provide for the plugin's manifest
        description = providers.fileContents(layout.projectDirectory.file("README.md")).asText.map {
            val start = "<!-- Plugin description -->"
            val end = "<!-- Plugin description end -->"

            with(it.lines()) {
                if (!containsAll(listOf(start, end))) {
                    throw GradleException("Plugin description section not found in README.md:\n$start ... $end")
                }
                subList(indexOf(start) + 1, indexOf(end)).joinToString("\n").let(::markdownToHTML)
            }
        }

        ideaVersion {
            sinceBuild = properties("pluginSinceBuild")
            untilBuild = properties("pluginUntilBuild")
        }

    }

    pluginVerification {
        ides {
//            ide(IntelliJPlatformType.IntellijIdeaCommunity,"2022.3")
//            ide(IntelliJPlatformType.IntellijIdeaCommunity,platformVersion.get())
//            ide(IntelliJPlatformType.AndroidStudio,"2024.2.1.9")
            recommended()
        }
    }
}

// Configure Gradle Changelog Plugin - read more: https://github.com/JetBrains/gradle-changelog-plugin
changelog {
    groups.empty()
    repositoryUrl = properties("pluginRepositoryUrl")
}

tasks {
    wrapper {
        gradleVersion = properties("gradleVersion").get()
    }
    buildSearchableOptions {
        enabled = false
    }
    jarSearchableOptions {
        enabled = false
    }
    patchPluginXml {
        version = properties("pluginVersion").get()
        sinceBuild = properties("pluginSinceBuild").get()
        untilBuild = properties("pluginUntilBuild").get()


        val changelog = project.changelog // local variable for configuration cache compatibility
        // Get the latest available change notes from the changelog file
        changeNotes = provider {
            changelog.getAll()
                .filter { !it.value.isUnreleased }
                .map {
                    changelog.renderItem(it.value, Changelog.OutputType.HTML)
                }.joinToString("\n")
        }
    }

    signPlugin {
        enabled = false
    }
//
//    publishPlugin {
//        dependsOn("patchChangelog")
//        token = environment("PUBLISH_TOKEN")
//        // The pluginVersion is based on the SemVer (https://semver.org) and supports pre-release labels, like 2.1.7-alpha.3
//        // Specify pre-release label to publish the plugin in a custom Release Channel automatically. Read more:
//        // https://plugins.jetbrains.com/docs/intellij/deployment.html#specifying-a-release-channel
//        channels = properties("pluginVersion").map { listOf(it.substringAfter('-', "").substringBefore('.').ifEmpty { "default" }) }
//    }

    compileJava{
        enabled = false
    }
//    compileKotlin {
//        enabled = false
//    }
    classes {
        enabled = false
    }
    instrumentCode {
        enabled = false
    }

    publishPlugin {
        enabled = false
    }
}
