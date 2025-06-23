package ru.kpfu.itis.quiz.feature.authentication.domain.usecase

interface RegisterUserUseCase {
    suspend operator fun invoke(username: String, password: String, confirmPassword: String): Boolean
}
