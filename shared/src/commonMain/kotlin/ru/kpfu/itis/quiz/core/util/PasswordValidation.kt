package ru.kpfu.itis.quiz.core.util

private const val MIN_PASSWORD_LENGTH = 6

fun validatePassword(password: String): Boolean {
    var hasDigit = false
    var hasUpperCase = false
    var hasLowerCase = false
    for (letter in password) {
        if (letter.isDigit()) hasDigit = true
        if (letter.isLowerCase()) hasLowerCase = true
        if (letter.isUpperCase()) hasUpperCase = true
    }
    return hasDigit && hasUpperCase && hasLowerCase && (password.length >= MIN_PASSWORD_LENGTH)
}

const val passwordRequirements = ""
