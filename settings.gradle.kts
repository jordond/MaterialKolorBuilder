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

include(":composeApp")
includeBuild("materialkolor") {
    dependencySubstitution {
        substitute(module("com.materialkolor:material-kolor")).using(project(":material-kolor"))
        substitute(module("com.materialkolor:material-color-utilities")).using(project(":material-color-utilities"))
    }
}