package ru.kpfu.itis.quiz.feature.profile.domain.usecase

import ru.kpfu.itis.quiz.feature.profile.domain.model.UserWithResults

interface GetCurrentUserUseCase {
    suspend operator fun invoke(): UserWithResults?
}
