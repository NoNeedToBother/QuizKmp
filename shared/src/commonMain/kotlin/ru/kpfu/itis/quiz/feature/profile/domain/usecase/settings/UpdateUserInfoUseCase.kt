package ru.kpfu.itis.quiz.feature.profile.domain.usecase.settings

interface UpdateUserInfoUseCase {
    suspend operator fun invoke(info: String?, username: String?)
}
