@file:Suppress("UnstableApiUsage")

rootProject.name = "MaterialKolorBuilder"

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("android.*")
            }
        }
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("android.*")
            }
        }
        mavenCentral()
    }
}

plugins {
    id("com.gradle.develocity") version "4.2.2"

    //https://github.com/JetBrains/compose-hot-reload?tab=readme-ov-file#set-up-automatic-provisioning-of-the-jetbrains-runtime-jbr-via-gradle
    id("org.gradle.toolchains.foojay-resolver-convention").version("1.0.0")
}

develocity {
    buildScan {
        termsOfUseUrl.set("https://gradle.com/help/legal-terms-of-use")
        termsOfUseAgree.set("yes")

        publishing.onlyIf { context ->
            context.buildResult.failures.isNotEmpty() && !System.getenv("CI").isNullOrEmpty()
        }
    }
}

include(":app")

// When the CI is building release versions of the app we want to use only published versions of the
// MaterialKolor library.
val useSubmodule = System.getenv("USE_MATERIAL_KOLOR_SUBMODULE").toBoolean()
if (useSubmodule) {
    includeBuild("library") {
        dependencySubstitution {
            substitute(module("com.materialkolor:material-kolor")).using(project(":material-kolor"))
            substitute(module("com.materialkolor:material-color-utilities")).using(project(":material-color-utilities"))
        }
    }
}
