package ru.kpfu.itis.quiz.feature.profile.domain.usecase

import ru.kpfu.itis.quiz.feature.profile.domain.model.UserWithResults

interface GetUserUseCase {
    suspend operator fun invoke(id: Long): UserWithResults?
}
