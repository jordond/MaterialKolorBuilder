package com.materialkolor.builder.version

import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime

@Serializable
data class CachedVersions(
    val stable: String,
    val prerelease: String?,
    val cachedAt: Long,
) {
    @OptIn(ExperimentalTime::class)
    fun isExpired(): Boolean {
        val now = Clock.System.now().toEpochMilliseconds()
        val age = now - cachedAt
        return age > CACHE_DURATION_MS
    }

    fun toVersions(): MaterialKolorVersions {
        return MaterialKolorVersions(stable = stable, prerelease = prerelease)
    }

    companion object {
        private val CACHE_DURATION_MS = 1.days.inWholeMilliseconds

        @OptIn(ExperimentalTime::class)
        fun from(versions: MaterialKolorVersions): CachedVersions {
            return CachedVersions(
                stable = versions.stable,
                prerelease = versions.prerelease,
                cachedAt = Clock.System.now().toEpochMilliseconds(),
            )
        }
    }
}
