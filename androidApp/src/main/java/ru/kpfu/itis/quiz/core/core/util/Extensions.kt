package ru.kpfu.itis.quiz.core.core.util

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
