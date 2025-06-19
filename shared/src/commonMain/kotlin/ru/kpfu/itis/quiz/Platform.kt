package ru.kpfu.itis.quiz

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform