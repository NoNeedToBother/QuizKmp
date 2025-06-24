package ru.kpfu.itis.quiz.feature.profile.data.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import ru.kpfu.itis.quiz.feature.profile.domain.model.UserWithResults
import ru.kpfu.itis.quiz.feature.profile.domain.repository.UserRepository
import ru.kpfu.itis.quiz.feature.profile.domain.usecase.GetCurrentUserUseCase

class GetCurrentUserUseCaseImpl(
    private val userRepository: UserRepository
) : GetCurrentUserUseCase {
    override suspend fun invoke(): UserWithResults? {
        return withContext(Dispatchers.IO) {
            userRepository.getSignedInUser()
        }
    }
}
