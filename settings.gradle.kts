@file:Suppress("UnstableApiUsage")

rootProject.name = "MaterialKolorBuilder"

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

plugins {
    id("com.gradle.develocity") version "3.19"
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
