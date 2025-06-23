package ru.kpfu.itis.quiz.core.util

private const val MIN_USERNAME_LENGTH = 7

fun validateUsername(param: String): Boolean {
    return param.length >= MIN_USERNAME_LENGTH
}
