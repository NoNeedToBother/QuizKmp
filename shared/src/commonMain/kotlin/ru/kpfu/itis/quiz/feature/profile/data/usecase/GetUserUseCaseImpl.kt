package ru.kpfu.itis.quiz.feature.profile.data.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import ru.kpfu.itis.quiz.feature.profile.domain.model.UserWithResults
import ru.kpfu.itis.quiz.feature.profile.domain.repository.UserRepository
import ru.kpfu.itis.quiz.feature.profile.domain.usecase.GetUserUseCase

class GetUserUseCaseImpl(
    private val userRepository: UserRepository
) : GetUserUseCase {
    override suspend fun invoke(id: Long): UserWithResults? {
        return withContext(Dispatchers.IO) {
            userRepository.getUser(id)
        }
    }
}
