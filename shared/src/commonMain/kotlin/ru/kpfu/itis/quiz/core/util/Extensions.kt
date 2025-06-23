package ru.kpfu.itis.quiz.core.util

import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.algorithms.SHA512
import io.ktor.utils.io.core.toByteArray

fun String.encryptBlocking() = CryptographyProvider.Default
    .get(SHA512)
    .hasher()
    .hashBlocking(toByteArray())
    .decodeToString()

suspend fun String.encrypt() = CryptographyProvider.Default
    .get(SHA512)
    .hasher()
    .hash(toByteArray())
    .decodeToString()

fun String.normalizeEnumName(): String {
    return this
        .lowercase()
        .replace("_", " ")
        .replaceFirstChar {
            if (it.isLowerCase()) it.titlecase()
            else it.toString()
        }
}

fun String.toEnumName(): String {
    return this
        .uppercase()
        .replace(" ", "_")
}
