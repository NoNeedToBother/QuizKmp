package ru.kpfu.itis.quiz.feature.profile.domain.usecase.settings

interface UpdateCredentialsUseCase {
    suspend operator fun invoke(password: String)
}
