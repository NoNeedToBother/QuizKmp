package ru.kpfu.itis.quiz.feature.authentication.domain.usecase

interface SignInUserUseCase {
    suspend operator fun invoke(username: String, password: String): Boolean
}
