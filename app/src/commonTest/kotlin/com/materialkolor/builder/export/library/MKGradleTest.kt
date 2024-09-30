package com.materialkolor.builder.export.library

import com.materialkolor.builder.BuildKonfig
import kotlin.test.Test
import kotlin.test.assertEquals

class MKGradleTest {

    private val mkVersion = BuildKonfig.MATERIAL_KOLOR_VERSION
    private val mkLib = "com.materialkolor:material-kolor:$mkVersion"

    @Test
    fun testLibsVersionsToml() {
        val expected = """
            [versions]
            materialKolor = "$mkVersion"
            
            [libraries]
            materialKolor = "$mkLib"
        """.trimIndent()

        assertEquals(expected, libsVersionsToml())
    }

    @Test
    fun testBuildImplementationWithVersionCatalog() {
        val expected = "implementation(libs.materialKolor)"
        assertEquals(expected, buildImplementation(useVersionCatalog = true))
    }

    @Test
    fun testBuildImplementationWithoutVersionCatalog() {
        val expected = "implementation(\"$mkLib\")"
        assertEquals(expected, buildImplementation(useVersionCatalog = false))
    }

    @Test
    fun testGradleKtsMultiplatformWithVersionCatalog() {
        val expected = """
            kotlin {
                sourceSets {
                    commonMain.dependencies {
                       implementation(libs.materialKolor)
                    }
                }
            }
        """.trimIndent()

        assertEquals(expected, gradleKts(isMultiplatform = true, useVersionCatalog = true))
    }

    @Test
    fun testGradleKtsAndroidOnlyWithVersionCatalog() {
        val expected = """
            dependencies {
                implementation(libs.materialKolor)
            }
        """.trimIndent()

        assertEquals(expected, gradleKts(isMultiplatform = false, useVersionCatalog = true))
    }

    @Test
    fun testGradleKtsMultiplatformWithoutVersionCatalog() {
        val expected = """
            kotlin {
                sourceSets {
                    commonMain.dependencies {
                       implementation("$mkLib")
                    }
                }
            }
        """.trimIndent()

        assertEquals(expected, gradleKts(isMultiplatform = true, useVersionCatalog = false))
    }

    @Test
    fun testGradleKtsAndroidOnlyWithoutVersionCatalog() {
        val expected = """
            dependencies {
                implementation("$mkLib")
            }
        """.trimIndent()

        assertEquals(expected, gradleKts(isMultiplatform = false, useVersionCatalog = false))
    }
}
