package ru.kpfu.itis.quiz.feature.authentication.domain.usecase

import ru.kpfu.itis.quiz.core.model.User

interface GetSignedInUserUseCase {
    suspend operator fun invoke(): User?
}