package ru.kpfu.itis.quiz.feature.profile.domain.usecase.settings

interface UpdateProfilePictureUseCase {
    suspend operator fun invoke(uri: String)
}
